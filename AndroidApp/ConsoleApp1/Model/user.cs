using System;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApp1.Model
{
    public class user
    {
        public int uid { get; set; }
        public string no { get; set; }
        public string passwd { get; set; }
        public string name { get; set; }
        public string col { get; set; }
        public string deq { get; set; }
        public Nullable<int> @class { get; set; }
        public Nullable<int> gra { get; set; }

        public virtual ICollection<uc> ucs { get; set; }
    }
}

