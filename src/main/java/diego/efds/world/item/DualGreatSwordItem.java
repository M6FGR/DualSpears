package diego.efds.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

public class DualGreatSwordItem extends WeaponItem {
    public DualGreatSwordItem(Item.Properties build, Tier materialIn) {
        super(materialIn, 2, -2.0F, build);
    }
}
