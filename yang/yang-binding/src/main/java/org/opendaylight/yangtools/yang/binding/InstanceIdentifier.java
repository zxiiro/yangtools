/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.concepts.Immutable;
import org.opendaylight.yangtools.concepts.Mutable;

/**
 * Uniquely identifies instance of data tree.
 * 
 */
public class InstanceIdentifier implements Immutable {

    private final List<PathArgument> path;
    private final Class<? extends DataObject> targetType;

    public InstanceIdentifier(Class<? extends DataObject> type) {
        path = Collections.emptyList();
        this.targetType = type;
    }

    public InstanceIdentifier(List<PathArgument> path, Class<? extends DataObject> type) {
        this.path = Collections.<PathArgument> unmodifiableList(new ArrayList<>(path));
        this.targetType = type;
    }

    /**
     * 
     * @return path
     */
    public List<PathArgument> getPath() {
        return this.path;
    }

    public Class<?> getTargetType() {
        return this.targetType;
    }

    @Override
    public String toString() {
        return "InstanceIdentifier [path=" + path + "]";
    }

    /**
     * Path argument of instance identifier.
     * 
     * Interface which implementations are used as path components of the
     * instance path.
     * 
     * @author ttkacik
     * 
     */
    public interface PathArgument {

        Class<? extends DataObject> getType();

    }

    public static class Item<T extends DataObject> implements PathArgument {
        private final Class<T> type;

        public Item(Class<T> type) {
            this.type = type;
        }

        public Class<T> getType() {
            return type;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Item<?> other = (Item<?>) obj;
            if (type == null) {
                if (other.type != null)
                    return false;
            } else if (!type.equals(other.type))
                return false;
            return true;
        }
    }

    public static class IdentifiableItem<I extends Identifiable<T> & DataObject, T extends Identifier<I>> implements
            PathArgument {

        private final T key;
        private final Class<I> type;

        public IdentifiableItem(Class<I> type, T key) {
            this.type = type;
            this.key = key;
        }

        T getKey() {
            return this.key;
        }

        @Override
        public Class<I> getType() {
            return this.type;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj.hashCode() != hashCode()) {
                return false;
            }
            if (!(obj instanceof IdentifiableItem<?, ?>)) {
                return false;
            }
            IdentifiableItem<?, ?> foreign = (IdentifiableItem<?, ?>) obj;
            return key.equals(foreign.getKey());
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return type.getName()+"[key=" + key + "]";
        }
    }

    public interface InstanceIdentifierBuilder extends Builder<InstanceIdentifier> {

        <T extends DataObject> InstanceIdentifierBuilder node(Class<T> container);

        <I extends Identifiable<T> & DataObject, T extends Identifier<I>> InstanceIdentifierBuilder node(
                Class<I> listItem, T listKey);

    }

    public static InstanceIdentifierBuilder builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl implements InstanceIdentifierBuilder {

        private List<PathArgument> path;
        private Class<? extends DataObject> target = null;

        @Override
        public InstanceIdentifier toInstance() {
            List<PathArgument> immutablePath = Collections.unmodifiableList(new ArrayList<PathArgument>(path));
            return new InstanceIdentifier(immutablePath, target);
        }

        @Override
        public <T extends DataObject> InstanceIdentifierBuilder node(Class<T> container) {
            target = container;
            path.add(new Item<T>(container));
            return this;
        }

        @Override
        public <I extends Identifiable<T> & DataObject, T extends Identifier<I>> InstanceIdentifierBuilder node(
                Class<I> listItem, T listKey) {
            target = listItem;
            path.add(new IdentifiableItem<I, T>(listItem, listKey));
            return this;
        }

    }
}
