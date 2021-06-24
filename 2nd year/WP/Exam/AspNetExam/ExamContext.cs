using Microsoft.EntityFrameworkCore;

namespace AspNetExam
{
    public class ExamContext : DbContext
    {
        public ExamContext(DbContextOptions<ExamContext> options) : base(options)
        {
        }
    }
}
