
using System;

namespace ContactsSharp
{
	
	
	public partial class ConfirmDialog : Gtk.Dialog
	{

		public ConfirmDialog()
		{
			this.Build();
		}
		
		public ConfirmDialog(string message)
		{
			this.Build();
			this.confirmLabel.Text = message;
		}		
		
	}
}
