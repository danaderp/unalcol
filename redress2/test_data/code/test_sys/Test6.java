using System;

public class Test6
{
    public Test6()
	{
        thisisacall();

        boolean b = true;

        if (b)
        {
            doSomething();
        }
        else if (!b)
        {

            doSomething2();
        }
        else
        {
            doSomething3();
        }

        while (b)
        {
            doSomething4();
        }

        do
        {
            doSomething5();
            throw new Exception();
        } while (b);

        for (int i = 0; i < length; i++)
        {
            doSomething6();
            break;
        }

        switch (b)
        {
            case 1:
                doSomething7();
                break;
            case 2:
                doSomething7();
                break;
            default:
                break;
        }

        try
        {
            string s = null;
            ProcessString(s);
        }
        // Most specific: 
        catch (ArgumentNullException e)
        {
            Console.WriteLine("{0} First exception caught.", e);
        }
        // Least specific: 
        catch (Exception e)
        {
            Console.WriteLine("{0} Second exception caught.", e);
            throw;
        }

        return;

	}

    void method1()
    {
       
    }

    void method2()
    {
        thisisacall();
    }

    void method2()
    {
        thisisacall();

        boolean b = true;

        if (b)
        {
            doSomething();
        }else if(!b)
        {
            
            doSomething2();
        }
        else
        {
            doSomething3();
        }

        while (b)
        {
            doSomething4();
        }

        do
        {
            doSomething5();
            throw new Exception();
        } while (b);

        for (int i = 0; i < length; i++)
        {
            doSomething6();
            break;
        }

        switch (b)
        {
            case   1:
                doSomething7();
                break;
            case 2:
                doSomething7();
                break;
            default:
                break;
        }

        try
        {
            string s = null;
            ProcessString(s);
        }
        // Most specific: 
        catch (ArgumentNullException e)
        {
            Console.WriteLine("{0} First exception caught.", e);
        }
        // Least specific: 
        catch (Exception e)
        {
            Console.WriteLine("{0} Second exception caught.", e);
            throw;
        }

        return;
    }

    void method3()
    {
        int i = 0;
        int sum = 0;

        while (i < 10)
        {
            i++;
            sum += i;
        }

        // ?: conditional operator.
        string classify = (sum > 0) ? "positive" : "negative";

        Console.WriteLine(sum);

        for (iterable_type iterable_element : iterable) {
			
		}
    }
}
