using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab5.Parsers
{
    abstract class BaseParser
    {
        protected List<string> Urls { get; set; }

        protected BaseParser(List<string> urls)
        {
            Urls = urls;
        }
    }
}
