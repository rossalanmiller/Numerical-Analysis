import java.util.stream.Stream;

public class Matrix 
{
	private int rows;
	private int cols;
	private int[][] matrix;
	
	public Matrix(int[][] matrix)
	{
		this.matrix = matrix;
		this.rows = matrix.length;
		this.cols = matrix[0].length;
	}
	
	public Matrix(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.matrix = new int[rows][cols];
	}
	
	/** 
	 * Adds two Matrix objects together and returns the
	 * resulting summed matrix, throws an error if the 
	 * two matrices have incompatible dimensions
	 */
	public Matrix add(Matrix other)
	{
		if(this.rows != other.rows || this.cols != other.cols)
		{
			String message = "Hello World";
			throw new IncompatibleDimensionsException(message);
		}
		else
		{
			int[][] newMatrix = new int[this.rows][this.cols];
			for(int row = 0; row < this.rows; row++)
			{
				for(int col = 0; col < this.cols; col++)
				{
					newMatrix[row][col] = this.matrix[row][col] + other.matrix[row][col];
				}
			}
			return new Matrix(newMatrix);
		}
	}
	
	
	/** 
	 * Subtracts two Matrix objects together and returns the
	 * resulting difference matrix, throws an error if the 
	 * two matrices have incompatible dimensions
	 */
	public Matrix sub(Matrix other)
	{
		if(this.rows != other.rows || this.cols != other.cols)
		{
			String message = "Hello World";
			throw new IncompatibleDimensionsException(message);
		}
		else
		{
			int[][] newMatrix = new int[this.rows][this.cols];
			for(int row = 0; row < this.rows; row++)
			{
				for(int col = 0; col < this.cols; col++)
				{
					newMatrix[row][col] = this.matrix[row][col] - other.matrix[row][col];
				}
			}
			return new Matrix(newMatrix);
		}
	}
	
	
	/**
	 * Multiplies to matrices together and returns the resultant
	 * matrix
	 */
	public Matrix multiply(Matrix other)
	{
		if(this.cols != other.rows)
		{
			String message = "Hello World";
			throw new IncompatibleDimensionsException(message);
		}
		else
		{
			Matrix newMatrix = new Matrix(this.rows, this.cols);
			for(int newRow = 0; newRow < newMatrix.rows; newRow++)
			{
				for(int newCol = 0; newCol < newMatrix.cols; newCol++)
				{
					int sum = 0;
					for(int row = 0; row < this.rows; row++)
					{
						for(int col = 0; col < this.cols; col++)
						{
							sum += this.matrix[row][col] + other.matrix[col][row];
						}
					}
					newMatrix.matrix[newRow][newCol] = sum;
				}
			}
			return newMatrix;
		}
	}

	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(int row = 0; row < matrix.length; row++)
		{
			for(int col = 0; col < matrix[0].length; col++)
			{
				builder.append(matrix[row][col] + " ");
			}
			builder.append("\n");
		}
		return builder.toString();
		
	}
}
