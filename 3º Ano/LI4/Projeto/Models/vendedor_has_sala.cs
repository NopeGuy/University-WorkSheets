using System;
using System.Collections.Generic;

namespace Noitcua.Models;

public partial class vendedor_has_sala
{
    public int id_vendedor { get; set; }

    public int id_sala { get; set; }

    public virtual vendedor id_vendedorNavigation { get; set; }
}