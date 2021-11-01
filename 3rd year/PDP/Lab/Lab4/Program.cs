using Lab4.Parser;
using System.Collections.Generic;

namespace Lab4
{
    class Program
    {
        private static readonly List<string> URLS = new() {
            "www.cs.ubbcluj.ro/~motogna/LFTC",
            "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/",
            "www.cs.ubbcluj.ro/~forest"
        };

        static void Main()
        {
            new CallbackParser(URLS);
            new TaskParser(URLS);
            new AsyncAwaitParser(URLS);
        }
    }
}
