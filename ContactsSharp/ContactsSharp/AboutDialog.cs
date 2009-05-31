
using System;

namespace ContactsSharp
{	
	
	public partial class AboutDialog : Gtk.Dialog
	{

		public AboutDialog()
		{
			this.Build();
		}

		protected virtual void OnButtonOkClicked (object sender, System.EventArgs e)
		{
			this.Hide();
		}		
	}
}
