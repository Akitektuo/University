using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace WindowsForm
{
    public partial class Form1 : Form
    {
        private readonly string USERS_TABLE = "Users";
        private readonly string LOGS_TABLE = "Logs";
        private readonly string PK_LOGS_USERS = "FK__Logs__UserId__3F466844";

        private DataSet dataSet = new DataSet();
        private SqlConnection dbConnection;

        private SqlDataAdapter dataAdapterUsers, dataAdapterLogs;
        private BindingSource bindingUsers = new BindingSource();
        private BindingSource bindingLogs = new BindingSource();

        private void InitializeDatabase()
        {
            dbConnection = new SqlConnection("Data Source = DESKTOP-K0E4MHB\\SQLEXPRESS; " +
                "Initial Catalog = Expenses; Integrated Security = SSPI;");

            dataAdapterUsers = new SqlDataAdapter($"SELECT * FROM {USERS_TABLE}", dbConnection);
            dataAdapterLogs = new SqlDataAdapter($"SELECT * FROM {LOGS_TABLE}", dbConnection);

            new SqlCommandBuilder(dataAdapterLogs);

            dataAdapterUsers.Fill(dataSet, USERS_TABLE);
            dataAdapterLogs.Fill(dataSet, LOGS_TABLE);

            var dataRelation = new DataRelation(
                PK_LOGS_USERS,
                dataSet.Tables[USERS_TABLE].Columns["Id"],
                dataSet.Tables[LOGS_TABLE].Columns["UserId"]);
            dataSet.Relations.Add(dataRelation);
        }

        private void InitializeUI()
        {
            bindingUsers.DataSource = dataSet;
            bindingUsers.DataMember = USERS_TABLE;

            bindingLogs.DataSource = bindingUsers;
            bindingLogs.DataMember = PK_LOGS_USERS;

            dataGridUsers.DataSource = bindingUsers;
            dataGridLogs.DataSource = bindingLogs;
        }

        private void buttonUpdateDB_Click(object sender, EventArgs e)
        {
            dataAdapterLogs.Update(dataSet, LOGS_TABLE);
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            InitializeDatabase();
            InitializeUI();
        }

        public Form1()
        {
            InitializeComponent();
        }

    }
}
