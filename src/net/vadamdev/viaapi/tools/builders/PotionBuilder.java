package net.vadamdev.viaapi.tools.builders;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;

public class PotionBuilder {
    /**
     * @author VadamDev
     * @since 01.07.2021
     */

    private final ItemStack is;

    public PotionBuilder(ItemStack is){
        this.is = is;
    }

    public PotionBuilder(int amount, short meta){
        is = new ItemStack(Material.POTION, amount, meta);
    }

    public PotionBuilder clone() {
        return new PotionBuilder(is);
    }

    public PotionBuilder setName(String name) {
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        is.setItemMeta(im);
        return this;
    }

    public PotionBuilder addPotionEffect(PotionEffect effect) {
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.addCustomEffect(effect, false);
        is.setItemMeta(im);
        return this;
    }

    public PotionBuilder setItemFlags(ItemFlag flag) {
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.addItemFlags(flag);
        is.setItemMeta(im);
        return this;
    }

    public PotionBuilder setLore(String... lore) {
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }

    public PotionBuilder setLore(List lore) {
        PotionMeta im = (PotionMeta) is.getItemMeta();
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemStack toItemStack() {
        return is;
    }
}
