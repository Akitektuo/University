using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsForm
{
    public partial class Form1 : Form
    {
        private readonly string USERS_TABLE = "Users";
        private readonly string LOGS_TABLE = "Logs";

        private DataSet dataSet = new DataSet();
        private SqlConnection dbConnection;

        private SqlDataAdapter dataAdapterUsers, dataAdapterLogs;
        private BindingSource bindingUsers, bindingLogs;

        private void Form1_Load(object sender, EventArgs e)
        {
            dbConnection = new SqlConnection("Data Source = DESKTOP-K0E4MHB\\SQLEXPRESS; " +
                "Initial Catalog = Expenses; Integrated Security = SSPI;");

            dataAdapterUsers = new SqlDataAdapter($"SELECT * FROM {USERS_TABLE}", dbConnection);
            dataAdapterLogs = new SqlDataAdapter($"SELECT * FROM {LOGS_TABLE}", dbConnection);

            dataAdapterUsers.Fill(dataSet, USERS_TABLE);
            dataAdapterLogs.Fill(dataSet, LOGS_TABLE);

            var dataRelation = new DataRelation(
                "FK__Logs__UserId__3F466844",
                dataSet.Tables[USERS_TABLE].Columns["Id"],
                dataSet.Tables[LOGS_TABLE].Columns["UserId"]);
            dataSet.Relations.Add(dataRelation);

            bindingUsers = new BindingSource();
            bindingUsers.DataSource = dataSet;
            bindingUsers.DataMember = USERS_TABLE;

            bindingLogs = new BindingSource();
            bindingLogs.DataSource = bindingUsers;
            bindingLogs.DataMember = "FK__Logs__UserId__3F466844";

            dataGridUsers.DataSource = bindingUsers;
            dataGridLogs.DataSource = bindingLogs;
        }

        public Form1()
        {
            InitializeComponent();
        }

    }
}
