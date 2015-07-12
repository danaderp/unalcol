namespace test.test2
{

    namespace test3
    {
        namespace test4
        {
            // Declare the generic class. 
            public class GenericList<T>
            {
                void Add(T input) { }
                public class Nested3<T>
                {
                    public class Nested4<T>
                    {
                        public class Nested5
                        {
                        }
                    }
                }
            }
        }
    }

    namespace test5.test6
    {

        /**
         * 
         * Test comment
         * 
         **/
        class TestGenericList
        {
            private class ExampleClass { }
            static void Main()
            {
                // Declare a list of type int.
                GenericList<int> list1 = new GenericList<int>();

                // Declare a list of type string.
                GenericList<string> list2 = new GenericList<string>();

                // Declare a list of type ExampleClass.
                GenericList<ExampleClass> list3 = new GenericList<ExampleClass>();
            }

            public class Nested
            {
                private Container parent;

                public Nested()
                {
                }
                public Nested(Container parent)
                {
                    this.parent = parent;
                }

                public class Nested2<T>
                {
                }
            }

        }
    }


    
}
