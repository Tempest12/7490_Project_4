public class Collision
{
	public Intersectable object;
	public float time;

	public Collision(Intersectable object, float time)
	{
		this.object = object;

		this.time = time;
	}
}