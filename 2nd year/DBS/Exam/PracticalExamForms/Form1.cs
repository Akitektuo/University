using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace PracticalExamForms
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private readonly string DATABASE_NAME = "ProjectManager";
        private readonly string PARENT_TABLE = "Projects";
        private readonly string CHILD_TABLE = "Tasks";
        private readonly string FK_CHILD_PARENT = "FK__Tasks__ProjectId__4222D4EF";
        private readonly string PARENT_ID = "Id";
        private readonly string CHILD_PARENT_ID = "ProjectId";

        private DataSet dataSet = new DataSet();
        private SqlConnection dbConnection;

        private SqlDataAdapter dataAdapterParent, dataAdapterChild;
        private BindingSource bindingParent = new BindingSource();
        private BindingSource bindingChild = new BindingSource();

        private void InitializeDatabase()
        {
            dbConnection = new SqlConnection("Data Source = localhost\\SQLEXPRESS; " +
                $"Initial Catalog = {DATABASE_NAME}; Integrated Security = SSPI;");

            dataAdapterParent = new SqlDataAdapter($"SELECT * FROM {PARENT_TABLE}", dbConnection);
            dataAdapterChild = new SqlDataAdapter($"SELECT * FROM {CHILD_TABLE}", dbConnection);

            new SqlCommandBuilder(dataAdapterParent);
            new SqlCommandBuilder(dataAdapterChild);

            dataAdapterParent.Fill(dataSet, PARENT_TABLE);
            dataAdapterChild.Fill(dataSet, CHILD_TABLE);

            var dataRelation = new DataRelation(
                FK_CHILD_PARENT,
                dataSet.Tables[PARENT_TABLE].Columns[PARENT_ID],
                dataSet.Tables[CHILD_TABLE].Columns[CHILD_PARENT_ID]);
            dataSet.Relations.Add(dataRelation);
        }

        private void InitializeUI()
        {
            bindingParent.DataSource = dataSet;
            bindingParent.DataMember = PARENT_TABLE;

            bindingChild.DataSource = bindingParent;
            bindingChild.DataMember = FK_CHILD_PARENT;

            dataGridParent.DataSource = bindingParent;
            dataGridChild.DataSource = bindingChild;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            InitializeDatabase();
            InitializeUI();
        }

        private void buttonUpdateDB_Click(object sender, EventArgs e)
        {
            dataAdapterParent.Update(dataSet, PARENT_TABLE);
            dataAdapterChild.Update(dataSet, CHILD_TABLE);
        }
    }
}
