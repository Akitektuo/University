
namespace WindowsForm
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGridParent = new System.Windows.Forms.DataGridView();
            this.dataGridChild = new System.Windows.Forms.DataGridView();
            this.labelChild = new System.Windows.Forms.Label();
            this.labelParent = new System.Windows.Forms.Label();
            this.buttonUpdateDB = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridParent)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridChild)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridParent
            // 
            this.dataGridParent.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridParent.Location = new System.Drawing.Point(41, 30);
            this.dataGridParent.Name = "dataGridParent";
            this.dataGridParent.RowTemplate.Height = 25;
            this.dataGridParent.Size = new System.Drawing.Size(548, 127);
            this.dataGridParent.TabIndex = 0;
            // 
            // dataGridChild
            // 
            this.dataGridChild.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridChild.Location = new System.Drawing.Point(41, 195);
            this.dataGridChild.Name = "dataGridChild";
            this.dataGridChild.RowTemplate.Height = 25;
            this.dataGridChild.Size = new System.Drawing.Size(548, 116);
            this.dataGridChild.TabIndex = 1;
            // 
            // labelChild
            // 
            this.labelChild.AutoSize = true;
            this.labelChild.Location = new System.Drawing.Point(41, 177);
            this.labelChild.Name = "labelChild";
            this.labelChild.Size = new System.Drawing.Size(0, 15);
            this.labelChild.TabIndex = 2;
            // 
            // labelParent
            // 
            this.labelParent.AutoSize = true;
            this.labelParent.Location = new System.Drawing.Point(41, 12);
            this.labelParent.Name = "labelParent";
            this.labelParent.Size = new System.Drawing.Size(0, 15);
            this.labelParent.TabIndex = 3;
            // 
            // buttonUpdateDB
            // 
            this.buttonUpdateDB.Location = new System.Drawing.Point(687, 158);
            this.buttonUpdateDB.Name = "buttonUpdateDB";
            this.buttonUpdateDB.Size = new System.Drawing.Size(101, 34);
            this.buttonUpdateDB.TabIndex = 4;
            this.buttonUpdateDB.Text = "Update DB";
            this.buttonUpdateDB.UseVisualStyleBackColor = true;
            this.buttonUpdateDB.Click += new System.EventHandler(this.buttonUpdateDB_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(875, 394);
            this.Controls.Add(this.buttonUpdateDB);
            this.Controls.Add(this.labelParent);
            this.Controls.Add(this.labelChild);
            this.Controls.Add(this.dataGridChild);
            this.Controls.Add(this.dataGridParent);
            this.Name = "Form1";
            this.Text = "Database GUI";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridParent)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridChild)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridParent;
        private System.Windows.Forms.DataGridView dataGridChild;
        private System.Windows.Forms.Label labelParent;
        private System.Windows.Forms.Button buttonUpdateDB;
        private System.Windows.Forms.Label labelChild;
    }
}

