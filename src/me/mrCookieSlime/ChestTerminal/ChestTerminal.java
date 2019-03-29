package me.mrCookieSlime.ChestTerminal;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.events.ItemUseEvent;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.Slimefun.GEO.OreGenResource;
import me.mrCookieSlime.Slimefun.GEO.OreGenSystem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;
import me.mrCookieSlime.Slimefun.api.item_transport.CargoNet;

public class ChestTerminal extends JavaPlugin implements Listener {
	
	public static final ItemStack quartz = new CustomItem(new ItemStack(Material.QUARTZ), "\u00A7rMilky Quartz");
	
	@Override
	public void onEnable() {
		Category category = new Category(new CustomItem(SlimefunItems.CHEST_TERMINAL, "\u00A75Chest Terminal", "", "\u00A7a> Click to open"));
		
		final ItemStack wireless_terminal16 = new CustomItem(new ItemStack(Material.ITEM_FRAME), "\u00A73CT Wireless Access Terminal \u00A7b(16)", "\u00A78\u21E8 \u00A77Linked to: \u00A7cNowhere", "\u00A78\u21E8 \u00A77Range: \u00A7e16 Blocks", "\u00A7c\u00A7o\u00A78\u21E8 \u00A7e\u26A1 \u00A770 / 10 J", "", "\u00A77If this Block is linked to an Access Terminal", "\u00A77it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal \u00A77to link", "&7&eRight Click&7 to open the linked Terminal");
		final ItemStack wireless_terminal64 = new CustomItem(new ItemStack(Material.ITEM_FRAME), "\u00A73CT Wireless Access Terminal \u00A7b(64)", "\u00A78\u21E8 \u00A77Linked to: \u00A7cNowhere", "\u00A78\u21E8 \u00A77Range: \u00A7e64 Blocks", "\u00A7c\u00A7o\u00A78\u21E8 \u00A7e\u26A1 \u00A770 / 25 J", "", "\u00A77If this Block is linked to an Access Terminal", "\u00A77it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal \u00A77to link", "&7&eRight Click&7 to open the linked Terminal");
		final ItemStack wireless_terminal128 = new CustomItem(new ItemStack(Material.ITEM_FRAME), "\u00A73CT Wireless Access Terminal \u00A7b(128)", "\u00A78\u21E8 \u00A77Linked to: \u00A7cNowhere", "\u00A78\u21E8 \u00A77Range: \u00A7e128 Blocks", "\u00A7c\u00A7o\u00A78\u21E8 \u00A7e\u26A1 \u00A770 / 50 J", "", "\u00A77If this Block is linked to an Access Terminal", "\u00A77it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal \u00A77to link", "&7&eRight Click&7 to open the linked Terminal");
		final ItemStack wireless_terminalT = new CustomItem(new ItemStack(Material.ITEM_FRAME), "\u00A73CT Wireless Access Terminal \u00A7b(Transdimensional)", "\u00A78\u21E8 \u00A77Linked to: \u00A7cNowhere", "\u00A78\u21E8 \u00A77Range: \u00A7eUnlimited", "\u00A7c\u00A7o\u00A78\u21E8 \u00A7e\u26A1 \u00A770 / 50 J", "", "\u00A77If this Block is linked to an Access Terminal", "\u00A77it will be able to remotely access that Terminal", "", "&7&eRight Click on an Access Terminal \u00A77to link", "&7&eRight Click&7 to open the linked Terminal");
		final ItemStack drill = new CustomItem(new ItemStack(Material.IRON_BLOCK), "\u00A73Quartz Drill", "\u00A77Mines up Milky Quartz", "", "\u00A7c\u00A7l! \u00A7cMake sure to Geo-Scan the Chunk first");
		
		new QuartzDrill(category, drill, "QUARTZ_DRILL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {null, SlimefunItems.POWER_CRYSTAL, null, SlimefunItems.PLASTIC_SHEET, SlimefunItems.OIL_PUMP, SlimefunItems.PLASTIC_SHEET, SlimefunItems.COBALT_INGOT, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.COBALT_INGOT}) {
			
			@Override
			public int getSpeed() {
				return 1;
			}
			
			@Override
			public int getEnergyConsumption() {
				return 60;
			}
		}.registerChargeableBlock(512);

		new SlimefunItem(category, quartz, "MILKY_QUARTZ", new RecipeType(drill), new ItemStack[0]).register();
		
		new SlimefunItem(category, new CustomItem(SlimefunItems.CHEST_TERMINAL, "\u00A73CT Illuminated Panel", "\u00A77Crafting Component"), "CT_PANEL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.BLISTERING_INGOT_3, quartz, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, quartz, SlimefunItems.BLISTERING_INGOT_3, quartz})
		.register();
		
		new AccessTerminal(category, SlimefunItems.CHEST_TERMINAL, "CHEST_TERMINAL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER_3, quartz, SlimefunItems.POWER_CRYSTAL, SlimefunItem.getItem("CT_PANEL"), SlimefunItems.POWER_CRYSTAL, SlimefunItems.PLASTIC_SHEET, SlimefunItems.ENERGY_REGULATOR, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new ImportBus(category, SlimefunItems.CT_IMPORT_BUS, "CT_IMPORT_BUS", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.CARGO_INPUT, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.CARGO_MOTOR, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new ExportBus(category, SlimefunItems.CT_EXPORT_BUS, "CT_EXPORT_BUS", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {null, SlimefunItems.DAMASCUS_STEEL_INGOT, null, SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItem.getItem("CT_IMPORT_BUS"), SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.GOLD_10K, SlimefunItems.PLASTIC_SHEET})
		.register();
		
		new SlimefunItem(category, wireless_terminal16, "CT_WIRELESS_ACCESS_TERMINAL_16", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER, quartz, SlimefunItems.COBALT_INGOT, SlimefunItems.CHEST_TERMINAL, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY})
		.register(new ItemInteractionHandler() {
			
			@Override
			public boolean onRightClick(ItemUseEvent e, Player p, ItemStack stack) {
				if (SlimefunManager.isItemSimiliar(stack, wireless_terminal16, false)) {
					ItemMeta im = stack.getItemMeta();
					List<String> lore = im.getLore();
					if (lore.isEmpty()) return true;
					
					if (e.getClickedBlock() != null) {
						if (BlockStorage.check(e.getClickedBlock(), "CHEST_TERMINAL")) {
							lore.set(0, "\u00A78\u21E8 \u00A77Linked to: \u00A78" + e.getClickedBlock().getWorld().getName() + " X: " + e.getClickedBlock().getX() + " Y: " + e.getClickedBlock().getY() + " Z: " + e.getClickedBlock().getZ());
							p.sendMessage("\u00A7bLink established!");
							im.setLore(lore);
							stack.setItemMeta(im);
							p.getInventory().setItemInMainHand(stack);
						}
						else openRemoteTerminal(p, stack, lore.get(0), 16);
						e.setCancelled(true);
						PlayerInventory.update(p);
					}
					else openRemoteTerminal(p, stack, lore.get(0), 16);
					return true;
				}
				else return false;
			}
		});
		
		new SlimefunItem(category, wireless_terminal64, "CT_WIRELESS_ACCESS_TERMINAL_64", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER, quartz, SlimefunItems.COBALT_INGOT, wireless_terminal16, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY})
		.register(new ItemInteractionHandler() {
			
			@Override
			public boolean onRightClick(ItemUseEvent e, Player p, ItemStack stack) {
				if (SlimefunManager.isItemSimiliar(stack, wireless_terminal64, false)) {
					ItemMeta im = stack.getItemMeta();
					List<String> lore = im.getLore();
					if (lore.isEmpty()) return true;
					
					if (e.getClickedBlock() != null) {
						if (BlockStorage.check(e.getClickedBlock(), "CHEST_TERMINAL")) {
							lore.set(0, "\u00A78\u21E8 \u00A77Linked to: \u00A78" + e.getClickedBlock().getWorld().getName() + " X: " + e.getClickedBlock().getX() + " Y: " + e.getClickedBlock().getY() + " Z: " + e.getClickedBlock().getZ());
							p.sendMessage("\u00A7bLink established!");
							im.setLore(lore);
							stack.setItemMeta(im);
							p.getInventory().setItemInMainHand(stack);
						}
						else openRemoteTerminal(p, stack, lore.get(0), 64);
						e.setCancelled(true);
						PlayerInventory.update(p);
					}
					else openRemoteTerminal(p, stack, lore.get(0), 64);
					return true;
				}
				else return false;
			}
		});
		
		new SlimefunItem(category, wireless_terminal128, "CT_WIRELESS_ACCESS_TERMINAL_128", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER_2, quartz, SlimefunItems.COBALT_INGOT, wireless_terminal64, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY})
		.register(new ItemInteractionHandler() {
			
			@Override
			public boolean onRightClick(ItemUseEvent e, Player p, ItemStack stack) {
				if (SlimefunManager.isItemSimiliar(stack, wireless_terminal128, false)) {
					ItemMeta im = stack.getItemMeta();
					List<String> lore = im.getLore();
					if (lore.isEmpty()) return true;
					
					if (e.getClickedBlock() != null) {
						if (BlockStorage.check(e.getClickedBlock(), "CHEST_TERMINAL")) {
							lore.set(0, "\u00A78\u21E8 \u00A77Linked to: \u00A78" + e.getClickedBlock().getWorld().getName() + " X: " + e.getClickedBlock().getX() + " Y: " + e.getClickedBlock().getY() + " Z: " + e.getClickedBlock().getZ());
							p.sendMessage("\u00A7bLink established!");
							im.setLore(lore);
							stack.setItemMeta(im);
							p.getInventory().setItemInMainHand(stack);
						}
						else openRemoteTerminal(p, stack, lore.get(0), 128);
						e.setCancelled(true);
						PlayerInventory.update(p);
					}
					else openRemoteTerminal(p, stack, lore.get(0), 128);
					return true;
				}
				else return false;
			}
		});
		
		new SlimefunItem(category, wireless_terminalT, "CT_WIRELESS_ACCESS_TERMINAL_TRANSDIMENSIONAL", RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {quartz, SlimefunItems.GPS_TRANSMITTER_4, quartz, SlimefunItems.COBALT_INGOT, wireless_terminal128, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BATTERY})
		.register(new ItemInteractionHandler() {
			
			@Override
			public boolean onRightClick(ItemUseEvent e, Player p, ItemStack stack) {
				if (SlimefunManager.isItemSimiliar(stack, wireless_terminalT, false)) {
					ItemMeta im = stack.getItemMeta();
					List<String> lore = im.getLore();
					if (lore.isEmpty()) return true;
					
					if (e.getClickedBlock() != null) {
						if (BlockStorage.check(e.getClickedBlock(), "CHEST_TERMINAL")) {
							lore.set(0, "\u00A78\u21E8 \u00A77Linked to: \u00A78" + e.getClickedBlock().getWorld().getName() + " X: " + e.getClickedBlock().getX() + " Y: " + e.getClickedBlock().getY() + " Z: " + e.getClickedBlock().getZ());
							p.sendMessage("\u00A7bLink established!");
							im.setLore(lore);
							stack.setItemMeta(im);
							p.getInventory().setItemInMainHand(stack);
						}
						else openRemoteTerminal(p, stack, lore.get(0), -1);
						e.setCancelled(true);
						PlayerInventory.update(p);
					}
					else openRemoteTerminal(p, stack, lore.get(0), -1);
					return true;
				}
				else return false;
			}
		});
		
		OreGenSystem.registerResource(new OreGenResource() {
			
			@Override
			public String getName() {
				return "Milky Quartz";
			}
			
			@Override
			public String getMeasurementUnit() {
				return "Unit(s)";
			}
			
			@Override
			public ItemStack getIcon() {
				return new ItemStack(Material.QUARTZ);
			}
			
			@Override
			public int getDefaultSupply(Biome biome) {
				return CSCoreLib.randomizer().nextInt(6) + 1;
			}
		});
		
		CargoNet.EXTRA_CHANNELS = true;
	}

	private void openRemoteTerminal(Player p, ItemStack stack, String loc, int range) {
		if (loc.equals("\u00A78\u21E8 \u00A77Linked to: \u00A7cNowhere")) {
			p.sendMessage("\u00A74Failed \u00A7c- This Device has not been linked to a Chest Terminal!");
			return;
		}
		loc = loc.replace("\u00A78\u21E8 \u00A77Linked to: \u00A78", "");
		World world = Bukkit.getWorld(loc.split(" X: ")[0]);
		if (world == null) {
			p.sendMessage("\u00A74Failed \u00A7c- The Chest Terminal that this Device has been linked to no longer exists!");
			return;
		}
		int x = Integer.parseInt(loc.split(" X: ")[1].split(" Y: ")[0]);
		int y = Integer.parseInt(loc.split(" Y: ")[1].split(" Z: ")[0]);
		int z = Integer.parseInt(loc.split(" Z: ")[1]);
		
		Block block = world.getBlockAt(x, y, z);
		
		if (!BlockStorage.check(block, "CHEST_TERMINAL")) {
			p.sendMessage("\u00A74Failed \u00A7c- The Chest Terminal that this Device has been linked to no longer exists!");
			return;
		}
		
		float charge = ItemEnergy.getStoredEnergy(stack);
		if (charge < 0.5F) {
			p.sendMessage("\u00A74Failed \u00A7c- You are out of Energy!");
			return;
		}

		if (range > 0 && !world.getUID().equals(p.getWorld().getUID())) {
			p.sendMessage("\u00A74Failed \u00A7c- You are out of Range!");
			return;
		}
		if (range > 0 && block.getLocation().distance(p.getLocation()) > range) {
			p.sendMessage("\u00A74Failed \u00A7c- You are out of Range!");
			return;
		}

		p.getInventory().setItemInMainHand(ItemEnergy.chargeItem(stack, -0.5F));
		PlayerInventory.update(p);
		BlockStorage.getInventory(block).open(p);
	}
}
