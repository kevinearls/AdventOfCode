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

    // For debugging
    public void printAllLayers() {
        logger.fine("This firewall has " + layers.keySet().size() + " layers");
        List<Integer> ids = new ArrayList<>(layers.keySet());
        Collections.sort(ids);
        for (Integer id : ids ) {
            logger.fine(id + ": " + layers.get(id).size + " at " + layers.get(id).getCurrentPosition());
        }
    }

    public Map<Integer, Layer> getLayers() {
        return layers;
    }

    public Integer getMaxLayerId() {
        return maxLayerId;
    }

    public Integer calculateCrossingSeverity() {
        Integer severity = 0;
        List<Integer> layerIds = new ArrayList<>(layers.keySet());
        Collections.sort(layerIds);

        for (Integer picosecond = 0; picosecond <= maxLayerId; picosecond++) {
            Layer currentLayer = layers.get(picosecond);
            if (currentLayer != null ) {
                logger.fine("At layer " + currentLayer.getId() + "  at picosecond " + picosecond);
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

            //logger.fine("After layer " + currentLayer.getId());
            //printAllLayers();
            //logger.fine("-----------------------------------------------------------");

        }

        return severity;
    }
}
