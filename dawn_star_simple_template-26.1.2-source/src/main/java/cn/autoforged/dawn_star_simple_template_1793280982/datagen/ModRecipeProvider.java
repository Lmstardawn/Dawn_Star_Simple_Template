package cn.autoforged.dawn_star_simple_template_1793280982.datagen;

import cn.autoforged.dawn_star_simple_template_1793280982.ExampleMod;
import cn.autoforged.dawn_star_mod_1788251976.item.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Recipes for " + output.getModId();
    }

    // 26.1.2 的 RecipeBuilder.save(output, String) 对不带命名空间的 path 会用 Identifier.parse，
    // 默认落到 minecraft: 命名空间。原版 netherite_upgrade_smithing_template / *_armor_trim_smithing_template
    // 正是这些 id 的模板复制配方，直接同名保存会覆盖原版配方、破坏原版模板复制与纹饰链路。
    // 因此所有配方 id 必须显式带上本 mod 命名空间。
    private static ResourceKey<Recipe<?>> recipeKey(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, path));
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                // 下界合金升级模板。需求中的 dawn_star:smithing_upgrade_base 对应前置的真实物品
                // dawn_star_mod_1788251976:forge_upgrade_template（Forge Upgrade Base）。
                shaped(RecipeCategory.MISC, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                    .pattern("DTD")
                    .pattern("DND")
                    .pattern("DDD")
                    .define('D', Items.DIAMOND)
                    .define('T', ModItems.FORGE_UPGRADE_TEMPLATE)
                    .define('N', Items.NETHERRACK)
                    .unlockedBy("has_forge_upgrade_base", has(ModItems.FORGE_UPGRADE_TEMPLATE))
                    .save(this.output, recipeKey("netherite_upgrade_smithing_template"));

                trimTemplate("sentry_armor_trim_smithing_template", Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLESTONE);
                trimTemplate("vex_armor_trim_smithing_template", Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, Items.STONE);
                trimTemplate("wild_armor_trim_smithing_template", Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.MOSSY_COBBLESTONE);
                trimTemplate("coast_armor_trim_smithing_template", Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.GRAVEL);
                trimTemplate("dune_armor_trim_smithing_template", Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SANDSTONE);
                // 26.1.2 还没有 26.2 的 ColorCollection/WeatheringCopperCollection 聚合 API，
                // 因此染色黏土与铜块直接使用具体的物品常量。
                trimTemplate("raiser_armor_trim_smithing_template", Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WHITE_TERRACOTTA);
                trimTemplate("shaper_armor_trim_smithing_template", Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.YELLOW_TERRACOTTA);
                trimTemplate("host_armor_trim_smithing_template", Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.BLUE_TERRACOTTA);
                trimTemplate("ward_armor_trim_smithing_template", Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.COBBLED_DEEPSLATE);
                trimTemplate("silence_armor_trim_smithing_template", Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SCULK_CATALYST);
                trimTemplate("tide_armor_trim_smithing_template", Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.PRISMARINE);
                trimTemplate("snout_armor_trim_smithing_template", Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, Items.BLACKSTONE);
                trimTemplate("rib_armor_trim_smithing_template", Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, Items.NETHER_BRICKS);
                trimTemplate("eye_armor_trim_smithing_template", Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.END_STONE);
                trimTemplate("spire_armor_trim_smithing_template", Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.PURPUR_BLOCK);
                trimTemplate("flow_armor_trim_smithing_template", Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE, Items.BREEZE_ROD);

                // 向导纹饰：规格里 T 同时被基底和 terracotta 复用且多出未使用的 T2。
                // 按玩法意图取「基底在顶中、terracotta 在中心」的 DTD/DXD/DDD 形状。
                shaped(RecipeCategory.MISC, Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE)
                    .pattern("DTD")
                    .pattern("DXD")
                    .pattern("DDD")
                    .define('D', Items.DIAMOND)
                    .define('T', ModItems.FORGE_UPGRADE_TEMPLATE)
                    .define('X', Items.TERRACOTTA)
                    .unlockedBy("has_forge_upgrade_base", has(ModItems.FORGE_UPGRADE_TEMPLATE))
                    .save(this.output, recipeKey("wayfinder_armor_trim_smithing_template"));

                // 镶铆纹饰：铜块或涂蜡铜块均可。
                shaped(RecipeCategory.MISC, Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE)
                    .pattern("DTD")
                    .pattern("DCD")
                    .pattern("DDD")
                    .define('D', Items.DIAMOND)
                    .define('T', ModItems.FORGE_UPGRADE_TEMPLATE)
                    .define('C', Ingredient.of(Items.COPPER_BLOCK, Items.WAXED_COPPER_BLOCK))
                    .unlockedBy("has_forge_upgrade_base", has(ModItems.FORGE_UPGRADE_TEMPLATE))
                    .save(this.output, recipeKey("bolt_armor_trim_smithing_template"));

                // 星聚锻造升级。需求中的 dawn_star:starpower_smithing_upgrade 对应前置真实物品
                // dawn_star_mod_1788251976:star_forge_upgrade（Star Forge Upgrade）。
                shaped(RecipeCategory.MISC, ModItems.STAR_FORGE_UPGRADE, 2)
                    .pattern("DSD")
                    .pattern("DND")
                    .pattern("DDD")
                    .define('D', Items.DIAMOND)
                    .define('S', ModItems.STAR_FORGE_UPGRADE)
                    .define('N', Items.NETHERITE_INGOT)
                    .unlockedBy("has_star_forge_upgrade", has(ModItems.STAR_FORGE_UPGRADE))
                    .save(this.output, recipeKey("starpower_smithing_upgrade"));

                // 黎明锻造升级。需求中的 dawn_star:dawn_smithing_upgrade 对应前置真实物品
                // dawn_star_mod_1788251976:dawn_forge_upgrade（Dawn Forge Upgrade）。
                // 第三行中间为下界之星（原为钻石），其余格子保持不变。
                shaped(RecipeCategory.MISC, ModItems.DAWN_FORGE_UPGRADE, 2)
                    .pattern("DSD")
                    .pattern("DFD")
                    .pattern("DND")
                    .define('D', Items.DIAMOND)
                    .define('S', ModItems.DAWN_FORGE_UPGRADE)
                    .define('F', ModItems.ETERNAL_DAWN_STAR_FRAGMENT)
                    .define('N', Items.NETHER_STAR)
                    .unlockedBy("has_dawn_forge_upgrade", has(ModItems.DAWN_FORGE_UPGRADE))
                    .save(this.output, recipeKey("dawn_smithing_upgrade"));
            }

            private void trimTemplate(String id, Item result, Item material) {
                shaped(RecipeCategory.MISC, result)
                    .pattern("DTD")
                    .pattern("DMD")
                    .pattern("DDD")
                    .define('D', Items.DIAMOND)
                    .define('T', ModItems.FORGE_UPGRADE_TEMPLATE)
                    .define('M', material)
                    .unlockedBy("has_forge_upgrade_base", has(ModItems.FORGE_UPGRADE_TEMPLATE))
                    .save(this.output, recipeKey(id));
            }
        };
    }
}
