package org.foobar.minesweeper.ui;

public final class CoordinatePair {
	private final int row;
	private final int column;
	public static final CoordinatePair EMPTY = new CoordinatePair(0, 0);

	public CoordinatePair(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public boolean equals(int row, int column) {
		return row == this.row && column == this.column;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CoordinatePair)) {
			return false;
		}
		CoordinatePair p = (CoordinatePair) o;

		return row == p.row && column == p.column;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 31 * result + row;
		result = 31 * result + column;
		return result;
	}

	@Override
	public String toString() {
		return "(" + this.row + ", " + this.column + ")";
	}
}
