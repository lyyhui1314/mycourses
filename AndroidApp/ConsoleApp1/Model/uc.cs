using System;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApp1.Model
{
    public class uc
    {
        public int id { get; set; }
        public Nullable<int> uid { get; set; }
        public Nullable<int> cid { get; set; }

        public virtual cours cours { get; set; }
        public virtual user user { get; set; }
    }
}
