package net.artyrian.frontiers.definition.item;

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

public class HealthArmorItem extends ArmorItem
{
    private final Supplier<ItemAttributeModifiers> attributeModifiers;

    public HealthArmorItem(Holder<ArmorMaterial> material, Type type, Properties settings)
    {
        super(material, type, settings);
        this.attributeModifiers = Suppliers.memoize(
                () -> {
                    // Basic builder for all
                    ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
                    EquipmentSlotGroup attributeModifierSlot = EquipmentSlotGroup.bySlot(type.getSlot());
                    ResourceLocation identifier = ResourceLocation.withDefaultNamespace("armor." + type.getName());

                    // Armor
                    int armor = material.value().getDefense(type);
                    builder.add(
                            Attributes.ARMOR,
                            new AttributeModifier(identifier, armor, AttributeModifier.Operation.ADD_VALUE),
                            attributeModifierSlot
                    );
                    // Toughness
                    float toughness = material.value().toughness();
                    builder.add(
                            Attributes.ARMOR_TOUGHNESS,
                            new AttributeModifier(identifier, toughness, AttributeModifier.Operation.ADD_VALUE),
                            attributeModifierSlot
                    );
                    // KB Resist
                    float kb_resist = material.value().knockbackResistance();
                    if (kb_resist > 0.0F) {
                        builder.add(
                                Attributes.KNOCKBACK_RESISTANCE,
                                new AttributeModifier(identifier, kb_resist, AttributeModifier.Operation.ADD_VALUE),
                                attributeModifierSlot
                        );
                    }

                    builder.add(
                            Attributes.MAX_HEALTH,
                            new AttributeModifier(identifier, 2.0, AttributeModifier.Operation.ADD_VALUE),
                            attributeModifierSlot
                    );

                    builder.add(
                            Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(identifier, -0.75, AttributeModifier.Operation.ADD_VALUE),
                            attributeModifierSlot
                    );

                    return builder.build();
                }
        );
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() { return this.attributeModifiers.get(); }
}

