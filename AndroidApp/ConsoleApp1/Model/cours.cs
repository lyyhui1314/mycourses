using System;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApp1.Model
{
    public class cours
    {
        public int cid { get; set; }
        public string coursename { get; set; }
        public string teacher { get; set; }
        public string classroom { get; set; }
        public Nullable<int> day { get; set; }
        public Nullable<int> classstart { get; set; }
        public Nullable<int> classend { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<uc> ucs { get; set; }
    }
}
