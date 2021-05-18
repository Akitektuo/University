using Assignment9.Models;
using System.Collections.Generic;
using System.Linq;

namespace Assignment9.Services
{
    public class TypeService : ITypeService
    {
        private RecipeContext context;

        public TypeService(RecipeContext context)
        {
            this.context = context;
        }

        public List<Type> GetAll()
        {
            return context.Types.ToList();
        }
    }
}
