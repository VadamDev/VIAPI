package net.vadamdev.viaapi.tools.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author ?, edited by VadamDev
 */
public class InventorySerializer {
    public static String itemStackArrayToBase64(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for(ItemStack item : items)
                dataOutput.writeObject(item);

            dataOutput.close();

            return Base64Coder.encodeLines(outputStream.toByteArray());
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String inventoryToBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); ++i)
                dataOutput.writeObject(inventory.getItem(i));

            dataOutput.close();

            return Base64Coder.encodeLines(outputStream.toByteArray());
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Inventory inventoryFromBase64(String data) {
        try {
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)));

            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            for(int i = 0; i < inventory.getSize(); ++i)
                inventory.setItem(i, (ItemStack)dataInput.readObject());

            dataInput.close();

            return inventory;
        }catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ItemStack[] itemStackArrayFromBase64(String data) {
        try {
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)));

            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; ++i)
                items[i] = (ItemStack)dataInput.readObject();

            dataInput.close();

            return items;
        }catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return new ItemStack[0];
    }
}
