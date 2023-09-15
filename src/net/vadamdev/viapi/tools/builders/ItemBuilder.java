package net.vadamdev.viapi.tools.builders;

import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author VadamDev
 * @since 04/08/2022
 */
public final class ItemBuilder {
    private static final JsonParser PARSER = new JsonParser();

    private ItemBuilder() {}

    /*
       Basic Itemstack
     */

    public static ItemstackBuilder item(Material material) {
        return new ItemstackBuilder(new ItemStack(material));
    }

    public static ItemstackBuilder item(Material material, int amount) {
        return new ItemstackBuilder(new ItemStack(material, amount));
    }

    public static ItemstackBuilder item(Material material, int amount, short data) {
        return new ItemstackBuilder(new ItemStack(material, amount, data));
    }

    public static ItemstackBuilder item(ItemStack itemStack) {
        return new ItemstackBuilder(itemStack);
    }

    /*
       Banners
     */

    public static BannerBuilder banner(ItemStack itemStack) {
        if(!itemStack.getType().equals(Material.BANNER))
            throw new InvalidParameterException("Provided ItemStack must be a banner");

        return new BannerBuilder(itemStack);
    }

    public static BannerBuilder banner() {
        return new BannerBuilder(new ItemStack(Material.BANNER));
    }

    /*
       Books
     */

    public static BookBuilder book(ItemStack itemStack) {
        if(!itemStack.getType().equals(Material.WRITTEN_BOOK) && !itemStack.getType().equals(Material.BOOK_AND_QUILL))
            throw new InvalidParameterException("Provided ItemStack must be a written book or a book and quill");

        return new BookBuilder(itemStack);
    }

    public static BookBuilder book(boolean written) {
        return new BookBuilder(new ItemStack(written ? Material.WRITTEN_BOOK : Material.BOOK_AND_QUILL));
    }

    /*
       Fireworks
     */

    public static FireworkBuilder firework(ItemStack itemStack) {
        if(!itemStack.getType().equals(Material.FIREWORK))
            throw new InvalidParameterException("Provided ItemStack must be a firework");

        return new FireworkBuilder(itemStack);
    }

    public static FireworkBuilder firework() {
        return new FireworkBuilder(new ItemStack(Material.FIREWORK));
    }

    /*
       Leather Armor
     */

    public static LeatherArmorBuilder leatherArmor(ItemStack itemStack) {
        if(!itemStack.getType().equals(Material.LEATHER_HELMET) && !itemStack.getType().equals(Material.LEATHER_CHESTPLATE) && !itemStack.getType().equals(Material.LEATHER_LEGGINGS) && !itemStack.getType().equals(Material.LEATHER_BOOTS))
            throw new InvalidParameterException("Provided ItemStack must be a leather armor piece");

        return new LeatherArmorBuilder(itemStack);
    }

    public static LeatherArmorBuilder leatherArmor(Material material) {
        return new LeatherArmorBuilder(new ItemStack(material));
    }

    /*
       Potion
     */

    public static PotionBuilder potion(ItemStack itemStack) {
        if(!itemStack.getType().equals(Material.POTION))
            throw new InvalidParameterException("Provided ItemStack must be a potion");

        return new PotionBuilder(itemStack);
    }

    public static PotionBuilder potion() {
        return new PotionBuilder(new ItemStack(Material.POTION));
    }

    /*
       Skulls
     */

    public static SkullBuilder skull(ItemStack itemStack) {
        if(!itemStack.getType().equals(Material.SKULL_ITEM) || itemStack.getData().getData() != 3)
            throw new InvalidParameterException("Provided ItemStack must be a skull");

        return new SkullBuilder(itemStack);
    }

    public static SkullBuilder skull() {
        return new SkullBuilder(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
    }

    public static BasicBuilder<?> skull(String value) {
        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        final String skinUrl = PARSER.parse(new String(Base64.decodeBase64(value))).getAsJsonObject()
                .get("textures").getAsJsonObject()
                .get("SKIN").getAsJsonObject()
                .get("url").getAsString();

        profile.getProperties().put("textures", new Property("textures", new String(Base64.encodeBase64(("{textures:{SKIN:{url:" + skinUrl + "}}}").getBytes()))));

        final ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        itemStack.setItemMeta(headMeta);

        return new BasicBuilder<>(itemStack);
    }

    /*
       Builder classes
     */

    public static class BasicBuilder<T extends BasicBuilder<?>> {
        protected final ItemStack itemStack;
        protected final ItemMeta itemMeta;
        protected final MaterialData materialData;

        private BasicBuilder(ItemStack itemStack) {
            this.itemStack = itemStack;
            this.itemMeta = itemStack.getItemMeta();
            this.materialData = itemStack.getData();
        }

        public T setAmount(int amount) {
            itemStack.setAmount(amount);
            return (T) this;
        }

        public T setDurability(short durability) {
            itemStack.setDurability(durability);
            return (T) this;
        }

        public T setUnbreakable() {
            itemMeta.spigot().setUnbreakable(true);
            return (T) this;
        }

        public T setName(String name) {
            itemMeta.setDisplayName(name);
            return (T) this;
        }

        public T setLore(String... lore) {
            return setLore(Arrays.asList(lore));
        }

        public T setLore(List<String> lore) {
            itemMeta.setLore(lore);
            return (T) this;
        }

        public T addUnsafeEnchantment(Enchantment enchantment, int level) {
            itemStack.addUnsafeEnchantment(enchantment, level);
            return (T) this;
        }

        public T addEnchantment(Enchantment enchantment, int level) {
            itemMeta.addEnchant(enchantment, level, true);
            return (T) this;
        }

        public T removeEnchantment(Enchantment enchantment) {
            itemMeta.removeEnchant(enchantment);
            return (T) this;
        }

        public T addItemFlags(ItemFlag... itemFlags) {
            itemMeta.addItemFlags(itemFlags);
            return (T) this;
        }

        public T removeItemFlags(ItemFlag... itemFlags) {
            itemMeta.removeItemFlags(itemFlags);
            return (T) this;
        }

        public T setGlowing() {
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return (T) this;
        }

        public T setGlowing2() {
            itemStack.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return (T) this;
        }

        public T clone() {
            return (T) new BasicBuilder(build());
        }

        public ItemStack build() {
            itemStack.setData(materialData);
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
    }

    public static final class ItemstackBuilder extends BasicBuilder<ItemstackBuilder> {
        private ItemstackBuilder(ItemStack itemStack) {
            super(itemStack);
        }

        public ItemstackBuilder setType(Material material) {
            itemStack.setType(material);
            return this;
        }

        public ItemstackBuilder setData(byte data) {
            materialData.setData(data);
            return this;
        }
    }

    public static final class BannerBuilder extends BasicBuilder<BannerBuilder> {
        private final BannerMeta bannerMeta;

        private BannerBuilder(ItemStack itemStack) {
            super(itemStack);
            this.bannerMeta = (BannerMeta) itemMeta;
        }

        public BannerBuilder setBaseColor(DyeColor color) {
            bannerMeta.setBaseColor(color);
            return this;
        }

        public BannerBuilder setPatterns(List<Pattern> list) {
            bannerMeta.setPatterns(list);
            return this;
        }

        public BannerBuilder setPattern(int index, Pattern pattern) {
            bannerMeta.setPattern(index, pattern);
            return this;
        }

        public BannerBuilder addPattern(Pattern pattern) {
            bannerMeta.addPattern(pattern);
            return this;
        }

        public BannerBuilder removePattern(int index) {
            bannerMeta.removePattern(index);
            return this;
        }
    }

    public static final class BookBuilder extends BasicBuilder<BookBuilder> {
        private final BookMeta bookMeta;

        private BookBuilder(ItemStack itemStack) {
            super(itemStack);
            this.bookMeta = (BookMeta) itemMeta;
        }

        public BookBuilder setTitle(String title) {
            bookMeta.setTitle(title);
            return this;
        }

        public BookBuilder setAuthor(String author) {
            bookMeta.setAuthor(author);
            return this;
        }

        public BookBuilder setPage(int index, String data) {
            bookMeta.setPage(index, data);
            return this;
        }

        public BookBuilder setPages(List<String> list) {
            bookMeta.setPages(list);
            return this;
        }

        public BookBuilder setPages(String... pages) {
            bookMeta.setPages(pages);
            return this;
        }

        public BookBuilder addPage(String... pages) {
            bookMeta.addPage(pages);
            return this;
        }
    }

    public static final class FireworkBuilder extends BasicBuilder<FireworkBuilder> {
        private final FireworkMeta fireworkMeta;

        private FireworkBuilder(ItemStack itemStack) {
            super(itemStack);
            this.fireworkMeta = (FireworkMeta) itemMeta;
        }

        public FireworkBuilder addEffect(FireworkEffect effect) throws IllegalArgumentException {
            fireworkMeta.addEffect(effect);
            return this;
        }

        public FireworkBuilder addEffects(FireworkEffect... effects) throws IllegalArgumentException {
            fireworkMeta.addEffects(effects);
            return this;
        }

        public FireworkBuilder addEffects(Iterable<FireworkEffect> effects) throws IllegalArgumentException {
            fireworkMeta.addEffects(effects);
            return this;
        }

        public FireworkBuilder removeEffect(int index) throws IndexOutOfBoundsException {
            fireworkMeta.removeEffect(index);
            return this;
        }

        public FireworkBuilder clearEffects() {
            fireworkMeta.clearEffects();
            return this;
        }

        public FireworkBuilder setPower(int power) throws IllegalArgumentException {
            fireworkMeta.setPower(power);
            return this;
        }
    }

    public static final class LeatherArmorBuilder extends BasicBuilder<LeatherArmorBuilder> {
        private final LeatherArmorMeta leatherArmorMeta;

        private LeatherArmorBuilder(ItemStack itemStack) {
            super(itemStack);
            this.leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        }

        public LeatherArmorBuilder setColor(Color color) {
            leatherArmorMeta.setColor(color);
            return this;
        }
    }

    public static final class PotionBuilder extends BasicBuilder<PotionBuilder> {
        private final PotionMeta potionMeta;

        private PotionBuilder(ItemStack itemStack) {
            super(itemStack);
            this.potionMeta = (PotionMeta) itemMeta;
        }

        public PotionBuilder addCustomEffect(PotionEffect effect, boolean overwrite) {
            potionMeta.addCustomEffect(effect, overwrite);
            return this;
        }

        public PotionBuilder removeCustomEffect(PotionEffectType effectType) {
            potionMeta.removeCustomEffect(effectType);
            return this;
        }

        public PotionBuilder setMainEffect(PotionEffectType effectType) {
            potionMeta.setMainEffect(effectType);
            return this;
        }

        public PotionBuilder clearCustomEffects() {
            potionMeta.clearCustomEffects();
            return this;
        }
    }

    public static final class SkullBuilder extends BasicBuilder<SkullBuilder> {
        private final SkullMeta skullMeta;

        private SkullBuilder(ItemStack itemStack) {
            super(itemStack);
            this.skullMeta = (SkullMeta) itemMeta;
        }

        public SkullBuilder setOwner(String owner) {
            skullMeta.setOwner(owner);
            return this;
        }
    }
}
