package com.kevinearls.adventofcode.virus;

public class Location {
    private Integer row;
    private Integer column;

    public Location(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Location{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (row != null ? !row.equals(location.row) : location.row != null) return false;
        return column != null ? column.equals(location.column) : location.column == null;
    }

    @Override
    public int hashCode() {
        int result = row != null ? row.hashCode() : 0;
        result = 31 * result + (column != null ? column.hashCode() : 0);
        return result;
    }
}
