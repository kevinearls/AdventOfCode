package com.kevinearls.adventofcode.firewall;

import java.util.*;
import java.util.logging.Logger;

public class Firewall {
    private Integer maxLayerId = Integer.MIN_VALUE;
    private Map<Integer, Layer> layers = new HashMap<>();
    private static final Logger logger = Logger.getLogger(Firewall.class.getName());

    /**
     * I *think* we can assume the input is sorted by id
     *
     * @param description
     */
    public Firewall(List<String> description) {
        for (String entry : description) {
            entry = entry.replaceAll(":", "");
            String[] parts = entry.split("\\s+");
            if (parts.length != 2) {
                throw new RuntimeException("Wrong number of parts " + parts.length + " should be 2");
            }
            Integer layerId = Integer.valueOf(parts[0]);
            if (layerId > maxLayerId) {
                maxLayerId = layerId;
            }
            Integer size = Integer.valueOf(parts[1]);
            logger.fine("Creating layer with id " + layerId + " and size " + size);
            Layer layer = new Layer(layerId, size);
            layers.put(layerId, layer);
        }
    }

    public Firewall(Firewall toCopy) {
        this.maxLayerId = toCopy.maxLayerId;
        // TODO find an easier way to do a deep copy
        for (Integer id : toCopy.layers.keySet()) {
            Layer existing = toCopy.getLayers().get(id);
            Layer newLayer = new Layer(existing.getId(), existing.getSize(), existing.getCurrentPosition(), existing.increment);
            layers.put(id, newLayer);
        }
    }

    // For debugging
    public void printAllLayers() {
        logger.fine("This firewall has " + layers.keySet().size() + " layers");
        List<Integer> ids = new ArrayList<>(layers.keySet());
        Collections.sort(ids);
        for (Integer id : ids ) {
            System.out.println("id: " + id + " size " + layers.get(id).size + " position " + layers.get(id).getCurrentPosition());
        }
    }

    public Map<Integer, Layer> getLayers() {
        return layers;
    }

    public Integer getMaxLayerId() {
        return maxLayerId;
    }


    public void step() {
        for (Integer id : layers.keySet()) {
            Layer layer = layers.get(id);
            layer.move();
        }
    }

    public Integer calculateCrossingSeverity() {
        Integer severity = 0;

        for (Integer picosecond = 0; picosecond <= maxLayerId; picosecond++) {
            Layer currentLayer = layers.get(picosecond);
            if (currentLayer != null ) {
                if (currentLayer.getCurrentPosition() == 0) {  // this is my current position, right?
                    logger.fine("Caught at layer " + currentLayer.getId());
                    severity = severity + (currentLayer.getId() * currentLayer.getSize());
                }
            }

            // move all layers every picosecond
            for (Integer id : layers.keySet()) {
                Layer layer = layers.get(id);
                layer.move();
            }
        }

        return severity;
    }


    public boolean canCross() {
        for (Integer picosecond = 0; picosecond <= maxLayerId; picosecond++) {
            Layer currentLayer = layers.get(picosecond);
            if (currentLayer != null && currentLayer.getCurrentPosition() ==0) {
                return false;
            }

            // move all layers every picosecond
            for (Integer id : layers.keySet()) {
                Layer layer = layers.get(id);
                layer.move();
            }
        }

        return true;
    }
}
