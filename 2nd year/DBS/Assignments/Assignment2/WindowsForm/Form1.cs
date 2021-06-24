using System;
using System.Collections.Specialized;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace WindowsForm
{
    public partial class Form1 : Form
    {
        private static NameValueCollection configuration = ConfigurationManager.AppSettings;
        private readonly string PARENT_TABLE = configuration["ParentTable"];
        private readonly string CHILD_TABLE = configuration["ChildTable"];
        private readonly string PK_CHILD_PARENT = configuration["PkChildParent"];

        private DataSet dataSet = new DataSet();
        private SqlConnection dbConnection;

        private SqlDataAdapter dataAdapterparent, dataAdapterChild;
        private BindingSource bindingParent = new BindingSource();
        private BindingSource bindingChild = new BindingSource();

        private void InitializeDatabase()
        {
            dbConnection = new SqlConnection("Data Source = localhost\\SQLEXPRESS; " +
                "Initial Catalog = Expenses; Integrated Security = SSPI;");

            dataAdapterparent = new SqlDataAdapter($"SELECT * FROM {PARENT_TABLE}", dbConnection);
            dataAdapterChild = new SqlDataAdapter($"SELECT * FROM {CHILD_TABLE}", dbConnection);

            new SqlCommandBuilder(dataAdapterChild);

            dataAdapterparent.Fill(dataSet, PARENT_TABLE);
            dataAdapterChild.Fill(dataSet, CHILD_TABLE);

            var dataRelation = new DataRelation(
                PK_CHILD_PARENT,
                dataSet.Tables[PARENT_TABLE].Columns[configuration["ParentId"]],
                dataSet.Tables[CHILD_TABLE].Columns[configuration["ChildParentId"]]);
            dataSet.Relations.Add(dataRelation);
        }

        private void InitializeUI()
        {
            bindingParent.DataSource = dataSet;
            bindingParent.DataMember = PARENT_TABLE;

            bindingChild.DataSource = bindingParent;
            bindingChild.DataMember = PK_CHILD_PARENT;

            dataGridParent.DataSource = bindingParent;
            dataGridChild.DataSource = bindingChild;

            labelParent.Text = configuration["ParentGridName"] ?? "Not set";
            labelChild.Text = configuration["ChildGridName"] ?? "Not set";
        }

        private void buttonUpdateDB_Click(object sender, EventArgs e)
        {
            dataAdapterChild.Update(dataSet, CHILD_TABLE);
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
