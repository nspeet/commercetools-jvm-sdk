package io.sphere.sdk.products;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.AttributeAccess;
import io.sphere.sdk.attributes.NamedAttributeAccess;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * A container for attributes. This can be either a product variant or nested attribute.
 */
public interface AttributeContainer {
    List<Attribute> getAttributes();

    /**
     * Access one attribute of a specific name and type which is known in the first place, consult {@link NamedAttributeAccess} how to implement these.
     *
     * @throws io.sphere.sdk.json.JsonException if the type of attribute cannot be parsed
     *
     * @param accessor declaration of the name and type of the attribute
     * @param <T> the underlying type of the attribute
     * @return the value of the attribute, or Optional.empty if absent
     */
    <T> Optional<T> getAttribute(final NamedAttributeAccess<T> accessor);

    default <T> Optional<T> getAttribute(final String name, final AttributeAccess<T> accessor) {
        return getAttribute(accessor.ofName(name));
    }

    default boolean hasAttribute(String attributeName) {
        return getAttributes().stream().anyMatch(attr -> attr.getName().equals(attributeName));
    }

    default boolean hasAttribute(NamedAttributeAccess<?> namedAccess) {
        return getAttributes().stream().anyMatch(attr -> attr.getName().equals(namedAccess.getName()));
    }

    default Optional<Attribute> getAttribute(final String attributeName) {
        requireNonNull(attributeName);
        return getAttributes().stream().filter(attr -> attr.getName().equals(attributeName)).findAny();
    }

    static AttributeContainer of(List<Attribute> attributes) {
        return AttributeContainerImpl.of(attributes);
    }
}
