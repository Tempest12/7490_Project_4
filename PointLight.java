public class PointLight
{
	public Vector3f position;

	public Color4f color;

	public PointLight(float x, float y, float z, float red, float green, float blue, float alpha)
	{
		this.position = new Vector3f(x, y, z);

		this.color = new Color4f(red, green, blue, alpha);
	}
}