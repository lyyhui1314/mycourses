using ConsoleApp1.Model;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Text;

namespace ConsoleApp1
{
    public class EfContext : DbContext
    {
        public EfContext() : base("demo")
        {

        }

        public virtual DbSet<cours> courses { get; set; }
        public virtual DbSet<uc> ucs { get; set; }
        public virtual DbSet<user> users { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Conventions.Remove<PluralizingTableNameConvention>();
            base.OnModelCreating(modelBuilder);
        }
    }
}
