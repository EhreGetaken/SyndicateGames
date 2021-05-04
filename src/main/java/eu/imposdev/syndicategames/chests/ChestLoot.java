package eu.imposdev.syndicategames.chests;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestLoot {

    public static HashMap<ItemStack, String> LOOT_SET = new HashMap<>();
    public static HashMap<ItemStack, String> LOOT_SET_PREMIUM = new HashMap<>();
    private List<ItemStack> lootMaterials = new ArrayList<>();
    private Map<ItemStack, Integer> lootAmount = new HashMap<>();
    private List<ItemStack> lootMaterialsPremium = new ArrayList<>();
    private Map<ItemStack, Integer> lootAmountPremium = new HashMap<>();

    public List<ItemStack> getLoot() {
        if (lootMaterials.isEmpty()) {
            LOOT_SET.forEach((material, s) -> {
                String[] data = s.split(";");
                int percentage = Integer.parseInt(data[0]);
                int maxAmount = Integer.parseInt(data[1]);

                for (int i = 0; i < percentage; i++) {
                    lootMaterials.add(material);
                }
                lootAmount.put(material, maxAmount);
            });
        }
        Collections.shuffle(lootMaterials, new Random(2));
        return lootMaterials;
    }

    public List<ItemStack> getPremiumLoot() {
        if (lootAmountPremium.isEmpty()) {
            LOOT_SET_PREMIUM.forEach((material, s) -> {
                String[] data = s.split(";");
                int percentage = Integer.parseInt(data[0]);
                int maxAmount = Integer.parseInt(data[1]);

                for (int i = 0; i < percentage; i++) {
                    lootMaterialsPremium.add(material);
                }
                lootAmountPremium.put(material, maxAmount);
            });
        }
        Collections.shuffle(lootMaterialsPremium, new Random(2));
        return lootMaterialsPremium;
    }

    public void fillChest(Chest chest) {
        chest.getBlockInventory().clear();
        Integer[] chestAmount = {23, 23, 22, 21, 21, 20};

        Random rand = new Random();
        int randItems = rand.nextInt(chestAmount.length);

        for(int i = 0; i < chestAmount[randItems]; i++) {
            int randInt = rand.nextInt(getLoot().size());

            ItemStack mat = getLoot().get(randInt);

            int maxAmount = getLootAmount().get(mat);

            List<Integer> amounts = new ArrayList<>();

            ItemStack stack = null;

            if(maxAmount >= 1) {
                if (maxAmount == 64) {
                    amounts.add(64);
                    amounts.add(64);
                    amounts.add(63);
                    amounts.add(62);
                    amounts.add(61);
                    amounts.add(60);
                    amounts.add(59);
                    amounts.add(58);
                    amounts.add(57);
                } else if (maxAmount == 32) {
                    amounts.add(32);
                    amounts.add(32);
                    amounts.add(31);
                    amounts.add(30);
                    amounts.add(29);
                    amounts.add(28);
                    amounts.add(27);
                    amounts.add(26);
                    amounts.add(25);
                    amounts.add(24);
                    amounts.add(23);
                    amounts.add(22);
                    amounts.add(21);
                    amounts.add(20);
                } else if (maxAmount == 6) {
                    amounts.add(6);
                    amounts.add(6);
                    amounts.add(5);
                    amounts.add(4);
                }else if(maxAmount == 3) {
                    amounts.add(3);
                    amounts.add(2);
                    amounts.add(2);
                    amounts.add(1);
                    amounts.add(1);
                } else if(maxAmount == 2) {
                    amounts.add(2);
                    amounts.add(1);
                    amounts.add(1);
                    amounts.add(1);
                } else if (maxAmount == 1) {
                    amounts.add(1);
                    amounts.add(1);
                }
                Collections.shuffle(amounts, new Random(2));
                int randAmount = rand.nextInt(amounts.size());

                ItemStack itemStack;
                if (!mat.getType().equals(Material.POTION)) {
                    if (mat.getData().getData() == (byte) 0) {
                        itemStack = new ItemStack(mat.getType(), amounts.get(randAmount));
                    } else {
                        itemStack = new ItemStack(mat.getType(), amounts.get(randAmount), mat.getData().getData());
                    }
                    if (mat.getEnchantments().size() > 0) {
                        mat.getEnchantments().forEach(itemStack::addEnchantment);
                    }
                } else {
                    itemStack = new ItemStack(mat.getType(), amounts.get(randAmount), (short) mat.getDurability());
                }

                stack = itemStack;
            } else {
                ItemStack itemStack;
                if (!mat.getType().equals(Material.POTION)) {
                    if (mat.getData().getData() == (byte) 0) {
                        itemStack = new ItemStack(mat.getType(), 1);
                    } else {
                        itemStack = new ItemStack(mat.getType(), 1, mat.getData().getData());
                    }
                    if (mat.getEnchantments().size() > 0) {
                        mat.getEnchantments().forEach(itemStack::addEnchantment);
                    }
                } else {
                    itemStack = new ItemStack(mat.getType(), 1, (short) mat.getDurability());
                }
                stack = itemStack;
            }

            int slotInt = 0;

            if(chest.getBlockInventory().getSize() == 27) {
                slotInt = rand.nextInt(27);
            } else {
                slotInt = rand.nextInt(54);
            }

            chest.getBlockInventory().setItem(slotInt, stack);
        }
    }

    public void fillPremiumChest(Chest chest) {
        chest.getBlockInventory().clear();
        Integer[] chestAmount = {23, 23, 22, 21, 21, 20};

        Random rand = new Random();
        int randItems = rand.nextInt(chestAmount.length);

        for(int i = 0; i < chestAmount[randItems]; i++) {
            int randInt = rand.nextInt(getPremiumLoot().size());

            ItemStack mat = getPremiumLoot().get(randInt);

            int maxAmount = getLootAmountPremium().get(mat);

            List<Integer> amounts = new ArrayList<>();

            ItemStack stack = null;

            if(maxAmount >= 1) {
                if (maxAmount == 64) {
                    amounts.add(64);
                    amounts.add(64);
                    amounts.add(63);
                    amounts.add(62);
                    amounts.add(61);
                    amounts.add(60);
                    amounts.add(59);
                    amounts.add(58);
                    amounts.add(57);
                } else if (maxAmount == 32) {
                    amounts.add(32);
                    amounts.add(32);
                    amounts.add(31);
                    amounts.add(30);
                    amounts.add(29);
                    amounts.add(28);
                    amounts.add(27);
                    amounts.add(26);
                    amounts.add(25);
                    amounts.add(24);
                    amounts.add(23);
                    amounts.add(22);
                    amounts.add(21);
                    amounts.add(20);
                } else if (maxAmount == 6) {
                    amounts.add(6);
                    amounts.add(6);
                    amounts.add(5);
                    amounts.add(4);
                }else if(maxAmount == 3) {
                    amounts.add(3);
                    amounts.add(2);
                    amounts.add(2);
                    amounts.add(1);
                    amounts.add(1);
                } else if(maxAmount == 2) {
                    amounts.add(2);
                    amounts.add(1);
                    amounts.add(1);
                    amounts.add(1);
                } else if (maxAmount == 1) {
                    amounts.add(1);
                    amounts.add(1);
                }
                Collections.shuffle(amounts, new Random(2));
                int randAmount = rand.nextInt(amounts.size());

                ItemStack itemStack;
                if (!mat.getType().equals(Material.POTION)) {
                    if (mat.getData().getData() == (byte) 0) {
                        itemStack = new ItemStack(mat.getType(), amounts.get(randAmount));
                    } else {
                        itemStack = new ItemStack(mat.getType(), amounts.get(randAmount), mat.getData().getData());
                    }
                    if (mat.getEnchantments().size() > 0) {
                        mat.getEnchantments().forEach(itemStack::addEnchantment);
                    }
                } else {
                    itemStack = new ItemStack(mat.getType(), amounts.get(randAmount), (short) mat.getDurability());
                }

                stack = itemStack;
            } else {
                ItemStack itemStack;
                if (!mat.getType().equals(Material.POTION)) {
                    if (mat.getData().getData() == (byte) 0) {
                        itemStack = new ItemStack(mat.getType(), 1);
                    } else {
                        itemStack = new ItemStack(mat.getType(), 1, mat.getData().getData());
                    }
                    if (mat.getEnchantments().size() > 0) {
                        mat.getEnchantments().forEach(itemStack::addEnchantment);
                    }
                } else {
                    itemStack = new ItemStack(mat.getType(), 1, (short) mat.getDurability());
                }
                stack = itemStack;
            }

            int slotInt = 0;

            if(chest.getBlockInventory().getSize() == 27) {
                slotInt = rand.nextInt(27);
            } else {
                slotInt = rand.nextInt(54);
            }

            chest.getBlockInventory().setItem(slotInt, stack);
        }
    }

    public List<ItemStack> getLootMaterials() {
        return lootMaterials;
    }

    public Map<ItemStack, Integer> getLootAmount() {
        return lootAmount;
    }

    public List<ItemStack> getLootMaterialsPremium() {
        return lootMaterialsPremium;
    }

    public Map<ItemStack, Integer> getLootAmountPremium() {
        return lootAmountPremium;
    }

}
