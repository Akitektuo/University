using System;
using System.Data.SqlClient;
using System.Data;

namespace Assignment1
{
    class Program
    {
        private static readonly string USERS_TABLE = "Users";

        private static DataSet dataSet = new DataSet();
        private static SqlConnection dbConnection = new SqlConnection();

        private static SqlDataAdapter dataAdapter;
        private static SqlCommandBuilder commandBuilder;

        static void Main(string[] args)
        {
            dbConnection.ConnectionString = "Data Source = DESKTOP-K0E4MHB\\SQLEXPRESS; " +
                "Initial Catalog = Expenses; Integrated Security = SSPI";

            dataAdapter = new SqlDataAdapter($"SELECT * FROM {USERS_TABLE}", dbConnection);
            commandBuilder = new SqlCommandBuilder(dataAdapter);

            dataAdapter.Fill(dataSet, USERS_TABLE);

            //InsertUsers();

            PrintUsers();

            //InsertUser();

            //UpdateUser();

            //DeleteUser();

            Console.WriteLine($"Total users: {GetTotalUsers()}");

            Console.ReadLine();
        }

        private static void PrintUsers()
        {
            foreach (DataRow row in dataSet.Tables[USERS_TABLE].Rows)
            {
                Console.WriteLine($"{row["Id"]}. {row["Name"]}");
            }
        }

        private static void InsertUser()
        {
            var newRow = dataSet.Tables[USERS_TABLE].NewRow();
            newRow["Id"] = 3;
            newRow["Name"] = "George Petrescu";
            newRow["Email"] = "george@gmail.com";
            dataSet.Tables[USERS_TABLE].Rows.Add(newRow);
            dataAdapter.Update(dataSet, USERS_TABLE);
        }

        private static void UpdateUser()
        {
            var updateRow = dataSet.Tables[USERS_TABLE].Rows[2];
            updateRow["Email"] = "geo@gmail.com";
            dataAdapter.Update(dataSet, USERS_TABLE);
        }

        private static void DeleteUser()
        {
            var deleteRow = dataSet.Tables[USERS_TABLE].Rows[2];
            deleteRow.Delete();
            dataAdapter.Update(dataSet, USERS_TABLE);
        }

        private static int GetTotalUsers()
        {
            var query = $"SELECT COUNT(*) FROM {USERS_TABLE}";

            dbConnection.Open();
            var command = new SqlCommand(query, dbConnection);
            var count = Convert.ToInt32(command.ExecuteScalar());
            command.Dispose();
            dbConnection.Close();

            return count;
        }

        private static void InsertUsers()
        {
            var query = $"INSERT INTO {USERS_TABLE} VALUES (4, 'Ana', 'anaaremere@gmail.com'), (5, 'Ion', 'pamant@gamil.com')";

            dbConnection.Open();
            var command = new SqlCommand(query, dbConnection);
            command.ExecuteNonQuery();
            command.Dispose();
            dbConnection.Close();
        }
    }
}
