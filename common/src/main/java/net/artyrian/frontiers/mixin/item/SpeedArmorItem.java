package net.artyrian.frontiers.mixin.item;

import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.Supplier;

public class SpeedArmorItem extends ArmorItem
{
    private final Supplier<ItemAttributeModifiers> attributeModifiers;

    public SpeedArmorItem(Holder<ArmorMaterial> material, Type type, Properties settings)
    {
        super(material, type, settings);
        this.attributeModifiers = Suppliers.memoize(
                () -> {
                    // Basic builder for all
                    ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
                    EquipmentSlotGroup attributeModifierSlot = EquipmentSlotGroup.bySlot(type.getSlot());
                    ResourceLocation identifier = ResourceLocation.withDefaultNamespace("armor." + type.getName());

                    builder.add(
                            Attributes.MOVEMENT_SPEED,
                            new AttributeModifier(identifier, 0.8, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                            attributeModifierSlot
                    );

                    return builder.build();
                }
        );
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() { return this.attributeModifiers.get(); }
}
