/**
 * Vector3f is a class that represents a floating point Vector in 3 dimensions
 * @author Shane del Solar
 */
public class Vector3f
{
    public float x;
    public float y;
    public float z;
    

    public Vector3f()
    {
        this(0.0f);
    }
    /**
     * Constructor that takes in a float value, and then assign xzy to all be that value
     * @param all 
     */
    public Vector3f(float all)
    {
        this(all, all, all);
    }
    /**
     * Constructor for the Vectors that simply takes in it's x, y, and z values respectively
     * @param x float - the X value
     * @param y float - the Y value
     * @param z float - the Z value
     */
    public Vector3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }        
    
    /**
     * Constructor that takes in another vector object and performs a deep copy
     * @param that Vector3f the vector to clone
     */    
    public Vector3f(Vector3f that)
    {
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
    }
    
    /**
     * Normalizes the vector
     */
    public void normalize()
    {
        float length = this.magnitude();

        if(length != 0)
        { 
            this.x /= length;
            this.y /= length;
            this.z /= length;
        }   
    }
    
    /**
     * Returns the length/magnitude of the vector
     * @return float - vector's Length/magnitude
     */
    public float magnitude()
    {
        return (float)Math.sqrt((x * x) + (y * y) + (z * z));
    }
    
    public float distance(Vector3f that)
    {
        float xDelta = this.x - that.x;
        float yDelta = this.y - that.y;
        float zDelta = this.z - that.z;

        xDelta *= xDelta;
        yDelta *= yDelta;
        zDelta *= zDelta;
    
        return (float)Math.sqrt(xDelta + yDelta + zDelta);
    }
    
    /**
     * Returns the magnitude of the Vector Squared
     * @return float - square of the vector magnitude
     */
    public float magnitudeSquared()
    {
        return (x * x) + (y * y) + (z * z);
    }
    
    /**
     * Performs a Dot Product Operation using this vector as the first operand and the given as the second.
     * @param that Vector3f - operand 2
     * @return float - dot(this, that)
     */
    public float dotProduct(Vector3f that)
    {
        return (this.x * that.x) + (this.y * that.y) + (this.z * that.z);
    } 
    
    /**
     * Performs a Dot Product Operation using this vector as the first operand and the given as the second. And then squares it.
     * @param that Vector3f - operand 2
     * @return float - dot(this, that)^2 
     */
    public float dotProductSquared(Vector3f that)
    {
        float num = (this.x * that.x) + (this.y * that.y) + (this.z * that.z);
        return num * num;
    }
    
    public void copy(Vector3f that)
    {
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
    }

    /**
     * Performs a Cross Product Operation using this vector as the first operand and the given as the second Stores Result in this Vector
     * @param that Vector3f - operand 2
     */
    public void crossProduct(Vector3f that)
    {
        float tempX = this.y * that.z - this.z * that.y;
        float tempY = this.z * that.x - this.x * that.z;
        float tempZ = this.x * that.y - this.y * that.x;
        x = tempX;
        y = tempY;
        z = tempZ;
    }  

    /**
     * Adds the 2 Vectors together and stores the results in this vector
     * @param that Vector3f - 2nd operand
     */
    public void add(Vector3f that)
    {
        this.x += that.x;
        this.y += that.y;
        this.z += that.z;
    }

    public void set(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Subtracts the two vectors and stores the results in this vector
     * @param that Vector3f - 2nd operand
     */
    public void subtract(Vector3f that)
    {        
        this.x -= that.x;
        this.y -= that.y;
        this.z -= that.z;
    }
    
    /**
     * Scales this vector by a constant term. Stores the results in this vector
     * @param scalar float - constant Term
     */
    public void scale(float scalar)
    {
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }    
    
    public boolean equals(Vector3f that)
    {
        return ((this.x == that.x) && (this.y == that.y) && (this.z == that.z));
    }        
    
    public String toString()
    {
        return "X: " + this.x + " Y: " + this.y + " Z: " + this.z;
    }
	
    ////////////////////////////////////////////////////////////////////////////
    /////           Static Functions                                       /////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Performs a Dot Product Operation using the two given Vector.
     * @param one Vector3f -> operand 1
     * @param two Vector3f -> operand 2
     * @return float - dot(one, two) 
     */
    public static float dotProduct(Vector3f one, Vector3f two)
    {
        return (one.x * two.x) + (one.y * two.y) + (one.z * two.z);
    } 
    
    /**
     * Performs a Dot Product Operation using the two given Vector. And then squares it.
     * @param one Vector3f -> operand 1
     * @param two Vector3f -> operand 2
     * @return float - dot(one, two)^2 
     */
    public static float dotProductSquared(Vector3f one, Vector3f two)
    {
        float num = (one.x * two.x) + (one.y * two.y) + (one.z * two.z);
        return num * num;
    }
    
    /**
     * Adds the two vector together and returns the resulting vector
     * @param one Vector3f -> operand 1
     * @param two Vectpr3f -> operand 2
     * @return Vector3f -> the resulting vector one + two
     */
    public static Vector3f addVectors(Vector3f one, Vector3f two)
    {
        return new Vector3f(one.x + two.x, one.y + two.y, one.z + two.z);
    }
    
    /**
     * Performs a Cross Product and returns the resulting vector
     * @param one Vector3f -> operand 1
     * @param two Vector3f -> operand 2
     * @return Vector3f -> the resulting Vector one cross two
     */
    public static Vector3f crossProduct(Vector3f one, Vector3f two)
    {
        return new Vector3f(one.y * two.z - one.z * two.y, one.z * two.x - one.x * two.z, one.x * two.y - one.y * two.x);
    }        
    
    /**
     * Subtracts two from one and returns the resulting vector.
     * @param one Vector3f -> operand 1
     * @param two Vector3f -> operand 2
     * @return  Vector3f -> the resulting Vector one - two
     */
    public static Vector3f subtractVectors(Vector3f one, Vector3f two)
    {
        return new Vector3f(one.x - two.x, one.y - two.y, one.z - two.z);
    }
    
    /**
     * Scales the vector by the given constant and returns the resulting vector
     * @param vector Vector3f -> The Vector to be scaled.
     * @param scalar float -> the constant term
     * @return Vector3f -> scalar * Vector
     */
    public static Vector3f scale(Vector3f vector, float scalar)
    {
        return new Vector3f(vector.x * scalar, vector.y * scalar, vector.z * scalar);
    }
}
