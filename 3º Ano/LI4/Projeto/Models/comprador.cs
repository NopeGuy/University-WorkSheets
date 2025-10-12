using System;
using System.Collections.Generic;

namespace Noitcua.Models;

public partial class comprador
{
    public int id { get; set; }
    
    public int id_user { get; set; }

    public virtual utilizador id_userNavigation { get; set; }
}