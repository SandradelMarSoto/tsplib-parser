package com.mlaskows.tsplib;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private String name;
    private Type type;
    private int dimension;
    private int capacity;
    private StringBuilder comment = new StringBuilder();
    private EdgeWeightType edgeWeightType;
    private DisplayDataType displayDataType;
    private List<Node> nodes;

    public ItemBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder withType(Type type) {
        this.type = type;
        return this;
    }

    public ItemBuilder withEdgeWeightType(EdgeWeightType edgeWeightType) {
        this.edgeWeightType = edgeWeightType;
        return this;
    }

    public ItemBuilder withDimension(int dimension) {
        this.dimension = dimension;
        return this;
    }

    public ItemBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public ItemBuilder withComment(String comment) {
        this.comment.append(comment);
        return this;
    }

    public ItemBuilder withDisplayDataType(DisplayDataType displayDataType) {
        this.displayDataType = displayDataType;
        return this;
    }

    public ItemBuilder addNode(Node node) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        nodes.add(node);
        return this;
    }

    public Item build() {
        return new Item(name, type, edgeWeightType, dimension, capacity,
                comment.toString(), displayDataType, nodes);
    }
}