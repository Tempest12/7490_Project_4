/**
 * Class that represents a 3 sided face. AKA a triangle. Has 3 vertices.
 * @author Shane del Solar
 */
public class Face3f extends Intersectable
{
    public Vector3f vOne;
    public Vector3f vTwo;
    public Vector3f vThree;

    public Vector3f normal;

    public Vector3f edgeOne;
    public Vector3f edgeTwo;
	public Vector3f edgeThree;
	
    /**
     * Constructor for the Face3f class. Takes in 3 indices for it's vertex list, and a reference to the vertex list
     * @param vOne int -> index for the vertex list for the 1st vertex
     * @param vTwo int -> index for the vertex list for the 2nd vertex
     * @param vThree int -> index for the vertex list for the 3rd vertex
     * @param vList  Vector3f[] -> Vertex List reference
     */
    public Face3f(int id, Vector3f vOne, Vector3f vTwo, Vector3f vThree, Surface surface)
    {
        this.id = id;

        this.vOne   = new Vector3f(vOne);
        this.vTwo   = new Vector3f(vTwo);
        this.vThree = new Vector3f(vThree);

        this.surface = surface;
		
		this.edgeOne =   new Vector3f(vTwo);
		this.edgeOne.subtract(this.vOne);
		
		this.edgeTwo =   new Vector3f(vThree);
		this.edgeTwo.subtract(this.vOne);
		
		this.normal = Vector3f.crossProduct(edgeOne, edgeTwo);
		this.normal.normalize();
		
		this.edgeTwo =   new Vector3f(vThree);
		this.edgeTwo.subtract(this.vTwo);
		
		this.edgeThree = new Vector3f(vOne);
		this.edgeThree.subtract(this.vThree);
		
		
		
    }
      
    /**
     * Computes the time of intersection with the given ray. returns -1.0f if no intersection will occur, Also takes in a variable fudge factor
     * @param ray Ray3f -> Ray we are testing for
     * @param fudge float -> fudge Factor, for intersections.
     * @return  float -> Time of the intersection, or if no intersection occurs: -1.0f
     */
    public float intersectRay(Ray3f ray, float fudge)
    {       
        //Determine Direction *Not Sure About This*
        float d = - normal.dotProduct(vOne);
        
        //Determine the time of Intersection of the ray with the plane that the face is on.
        float time = (normal.dotProduct(ray.origin) + d) / (normal.dotProduct(ray.direction)) * -1.0f;
        
        //Calculate Intersection Point
        Vector3f intersection = ray.computeLocationAtTime(time);
        
        //Determine the Intersection Point's Location with respect to each side.
        float insideOne =   Vector3f.dotProduct(Vector3f.crossProduct( edgeOne,   Vector3f.subtractVectors(intersection, vOne  )), normal);
        float insideTwo =   Vector3f.dotProduct(Vector3f.crossProduct( edgeTwo,   Vector3f.subtractVectors(intersection, vTwo  )), normal);
        float insideThree = Vector3f.dotProduct(Vector3f.crossProduct( edgeThree, Vector3f.subtractVectors(intersection, vThree)), normal);        
        
        //See if the intersection point is inside the triangle, AKA it is inside all three edges
        if(insideOne >= fudge && insideTwo >= fudge && insideThree >= fudge)
        {
            //If so return the time.
            return time;
        }
        /*else if(insideOne < 0.0f && insideTwo < 0.0f && insideThree < 0.0f)
		{
			return time;
		}*/
		else
        {    
            //Return a negative number otherwise.
            return -1.0f;
        }
     }
    
    /**
     * Computes the time of intersection with the given ray. returns -1.0f if no intersection will occur. Uses a fudge factor of : - 0.00001f
     * @param ray Ray3f -> Ray we are testing for
     * @return  float -> Time of the intersection, or if no intersection occurs: -1.0f
     */
    @Override
    public float intersectRay(Ray3f ray)
    {
        return this.intersectRay(ray, -0.000001f);
    }
	
	public Vector3f findNormal(Ray3f ray, float time)
	{
		Vector3f hey = new Vector3f(this.normal);
		
		hey.scale(-1.0f);
		
		return hey;
	}
}