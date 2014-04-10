/**
 * Generic Sphere to be raytraced against.
 * @author Shane del Solar
 */
public class Sphere extends Intersectable
{
    public Vector3f position;

    public float radius;

    /**
     * Basic Constructor, takes in an XYZ location and a radius
     * @param x float -> the X position
     * @param y float -> the Y position
     * @param z float -> the Z position
     * @param radius float -> the Sphere's radius
     */
    public Sphere(int id, float x, float y, float z, float radius, Surface surface)
    {
        this.id = id;

        this.position = new Vector3f(x, y, z);

        this.radius = radius;

        this.surface = surface;
    }

    /**
     * Constructor, takes in a Sphere object and preforms a deep copy. *DEEP COPY*
     * @param that Sphere -> the sphere to copy
     */
    public Sphere(Sphere that)
    {   
        this.position = new Vector3f(that.position);

        this.radius = that.radius;

        this.surface = that.surface;
    }

    /**
     * Computes the time of intersection with the given Ray3f, or returns -1.0 if one does not occur
     * @param ray Ray3f -> Ray to test against
     * @return float -> time of the intersection, or -1.0f if no intersection occurs 
     */
    @Override
    public float intersectRay(Ray3f ray)
    {
        Vector3f v = Vector3f.subtractVectors(ray.origin, this.position);
        //Copmute the discriminent value of the quadractic equation. Meaning we can treat any imaginary results as misses
        float disc = v.dotProductSquared(ray.direction) - (v.magnitudeSquared() - (radius * radius));

        if (disc < 0)
        {
            return -1.0f;
        }

        //So a Result does exists, so let's compute the two intersection times(or the one in the certain case AKA Tangent Rays)
        float timeOne = -1 * v.dotProduct(ray.direction) + (float) Math.sqrt(disc);
        float timeTwo = -1 * v.dotProduct(ray.direction) - (float) Math.sqrt(disc);
        //Are both answers Real?
        if (timeOne < 0 || timeTwo < 0)
        {
            //Okay if not return the one that is.
            if (timeOne < 0 && timeTwo < 0)
            {
                return -1.0f;
            }
            else
            {
                if (timeOne < 0)
                {
                    return timeTwo;
                }
                else if (timeTwo < 0)
                {
                    return timeOne;
                }
                else
                {
                    //Error This should never happen
                    System.out.println("There has been an error in the Ray Tracing code for spheres... The Last \"else\" was reached with the following values: timeOne = " + timeOne + " amd timeTwo = " + timeTwo);
                    return -1.0f;
                }
                
            }
        }
        else
        {
            //Otherwise we have a normal intersection. In one side and out the other.
            //So now we return the one with the smallest time AKA closest to the ray's origin.
            return Math.min(timeOne, timeTwo);
        }
    }

    /**
     * Computes the normal of the Intersection point. *WARNING* Does not check for intersection
     * @param ray Ray3f -> Ray that is know to have intersected the sphere.e
     * @param time float -> time of the ray's intersection.
     * @return Vector3f -> Vector that represents the normal of the intersection point
     */
    public Vector3f findNormal(Ray3f ray, float time)
    {	
        Vector3f intersection = ray.computeLocationAtTime(time);
        intersection.subtract(this.position);
        intersection.normalize();
			
        return intersection;
    }
    
    public float[] findUV(Vector3f point)
    {
        float[] uv = new float[2];
        
        uv[0] = 0.0f;
        uv[1] = 0.0f;
        
        return uv;
    }
}
