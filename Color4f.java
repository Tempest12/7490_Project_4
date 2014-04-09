public class Color4f
{
	public float   red;
	public float green;
	public float  blue;
	public float alpha;

	public Color4f()
	{
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public Color4f(float all)
	{
		this(all, all, all, all);
	}

	public Color4f(float red, float green, float blue)
	{
		this(red, green, blue, 1.0f);
	}

	public Color4f(float red, float green, float blue, float alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public Color4f(Color4f that)
	{
		this(that.red, that.green, that.blue, that.alpha);
	}
	
	public void add(Color4f that)
	{
		this.red += that.red;
		this.green += that.green;
		this.blue += that.blue;
		this.alpha += that.alpha;
	}
	
	public void copy(Color4f that)
	{
	    this.red = that.red;
	    this.green = that.green;
	    this.blue = that.blue;
	    this.alpha = that.alpha;
	}
	
	public void scale(float scalar)
	{
		this.red *= scalar;
		this.green *= scalar;
		this.blue *= scalar;
		this.alpha *= scalar;
	}
	
	public void set(float red, float green, float blue)
	{
		this.set(red, green, blue, 1.0f);
	}

	public void set(float red, float green, float blue, float alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public String toString()
	{
		return "r: " + this.red + " g: " + this.green + " b: " + this.blue + " a: " + this.alpha;
	}
	
	public static Color4f multiplyColors(Color4f left, Color4f righ)
	{
		return new Color4f(left.red * righ.red, left.green * righ.green, left.blue * righ.blue, left.alpha * righ.alpha);
	}
}
