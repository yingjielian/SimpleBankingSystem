package Jack2025.SOFI;

import java.util.Stack;

/*This code simulates asteroid collisions using a stack, where positive numbers move right and negative numbers move left.
        When a left-moving asteroid meets right-moving ones in the stack, smaller right-moving asteroids are popped until
         either no collision is possible or both asteroids are the same size (and destroy each other).
        Finally, the remaining asteroids in the stack are extracted into an array in their original order and returned.*/

public class AsteroidCollision_735 {

    public static void main(String[] args)
    {
        System.out.println(asteroidCollision(new int[]{-2,-1,1,2}));
    }
    static public int[] asteroidCollision(int[] asteroids)
    {
        Stack<Integer> stack = new Stack<>();

        for(int a : asteroids)
        {
            if(a > 0) {
                stack.push(a);
            }
            else {
                // Collision and current as size is larger than opposite direction as
                while(!stack.isEmpty() && stack.peek() > 0 && stack.peek() < Math.abs(a))
                {
                    stack.pop();
                }

                // No Collision happens when there's no as or they are all moving to the left
                if(stack.isEmpty() || stack.peek() < 0)
                {
                    stack.push(a);
                }

                if(stack.peek() == Math.abs(a))
                {
                    stack.pop();
                }

            }
        }

        int[] res = new int[stack.size()];

        for(int i = stack.size() - 1; i >= 0; i--)
        {
            if(!stack.isEmpty())
            {
                res[i] = stack.pop();
            }
        }

        return res;
    }
}
