package com.kevinearls.adventofcode.bridge;

public class Component {
    private Integer portA;
    private Integer portB;

    public Component(Integer portA, Integer portB) {
       this.portA = portA;
       this.portB = portB;
    }

    public Integer strength() {
        return portA + portB;
    }

    public Integer getPortA() {
        return portA;
    }

    public Integer getPortB() {
        return portB;
    }

    public Integer getOtherPort(Integer target) {
        if (portA == target) {
            return portB;
        } else {
            return portA;
        }
    }

    @Override
    public String toString() {
        return portA + "/" + portB;
        /*return "Component{" +
                "portA=" + portA +
                ", portB=" + portB +
                '}';*/
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Component component = (Component) o;

        if (!portA.equals(component.portA)) return false;
        return portB.equals(component.portB);
    }

    @Override
    public int hashCode() {
        int result = portA.hashCode();
        result = 31 * result + portB.hashCode();
        return result;
    }
}
