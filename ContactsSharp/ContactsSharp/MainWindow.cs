using Gtk;
using Mono.Unix;
using System;

namespace ContactsSharp {

	public partial class MainWindow: Gtk.Window
	{	
		Boolean modified = false;
		Gtk.Entry entryFocus = null;
			
		public MainWindow (): base (Gtk.WindowType.Toplevel)
		{
			Build ();
		}
		
		public ContactsManager ContactsManager
		{
			get; set;
		}
			
		public int ContactNumber
		{
			get; set;
		}
			
		public int TotalContacts
		{
			get; set;
		}
		
		public Boolean Nuevo
		{
			get; set;
		}
		
		public AboutDialog AboutDialog
		{
			get; set;
		}
			
		public void SetContact(Contact contact)
		{
			nameEntry.Text = contact.Name;
			emailEntry.Text = contact.Email;
			descrEntry.Text = contact.Description;
			ContactNumber = contact.Id;
			modLabel.Text = "";
			
			if (TotalContacts > 0)
			{
				string showmsg = Catalog.GetString("Showing contact {0} of {1}");
				navLabel.Text = string.Format(showmsg, ContactNumber, TotalContacts);
				NewAction.Sensitive = true;
			}
			else
			{
				navLabel.Text = string.Format(Catalog.GetString("No contacts"));
			}
			
			if (ContactNumber < 2)
			{
				PreviousAction.Sensitive = false;
			}
			else
			{
				PreviousAction.Sensitive = true;
			}
			
			if (ContactNumber < TotalContacts)
			{
				NextAction.Sensitive = true;
			}
			else
			{
				NextAction.Sensitive = false;
			}
			
			// deshabilito guardar un elemento que acabo de mostrar
			SaveAction.Sensitive = false;
		}	
		
		public void ShowContact()			
		{
			Console.WriteLine("Showing {0} of {1}", ContactNumber, TotalContacts);
			if (ContactNumber <= TotalContacts)
			{
				Contact c = ContactsManager.getContact(ContactNumber);
				SetContact(c);
			}
		}
		
		public void SetModified()
		{
			modified = true;
			modLabel.Text = Catalog.GetString("Modified");
			SaveAction.Sensitive = true;
		}
		
		protected void OnDeleteEvent (object sender, DeleteEventArgs a)
		{
			OnSalirActionActivated(sender, a);
			// Application.Quit ();
			a.RetVal = true;
		}
	
		protected virtual void OnSalirActionActivated (object sender, System.EventArgs e)
		{
			if (modified) {
				if (confirm(Catalog.GetString("Exit with unsaved changes?")))
				{
					Application.Quit ();
				}
								
			} else {
				Application.Quit ();
			}
			
		}
	
		protected virtual void OnGuardarActionActivated (object sender, System.EventArgs e)
		{
			string name = nameEntry.Text;
			string email = emailEntry.Text;
			string description = descrEntry.Text;
			
			if (modified && !Nuevo)
			{
				Contact contact = new Contact(ContactNumber, name, email, description);
				ContactsManager.UpdateContact(contact);
				modLabel.Text = Catalog.GetString("Updated");
			}
			else
			{
				Contact contact = new Contact(TotalContacts+1, name, email, description);
				ContactsManager.SaveContact(contact);
				if (TotalContacts == 0)
				{
					TotalContacts++;
				}
				ContactNumber = TotalContacts;
				modLabel.Text = Catalog.GetString("Saved");
				Nuevo = false;
				ShowContact();
			}
			
			modified = false;
			ShowContact();
			
			// habilito el botón para crear un nuevo contacto
			NewAction.Sensitive = true;
		}
	
		protected virtual void OnAtrsActionActivated (object sender, System.EventArgs e)
		{			
			ContactNumber--;
			ShowContact();
		}
	
		protected virtual void OnAdelanteAction1Activated (object sender, System.EventArgs e)
		{
			ContactNumber++;
			ShowContact();
		}
		
		protected virtual void OnNuevoActionActivated (object sender, System.EventArgs e)
		{
			// Solo tiene sentido crear un nuevo elemento si el elemento mostrado no lo es
			if (!Nuevo)
			{
				Contact newContact = new Contact(TotalContacts+1, "", "", "");
				Nuevo = true;
				modified = false;
				TotalContacts++;
				ContactNumber = TotalContacts;
				SetContact(newContact);
			}
		}		

		protected virtual void OnNameEntryKeyPressEvent (object o, Gtk.KeyPressEventArgs args)
		{
			SetModified();
		}

		protected virtual void OnEmailEntryKeyPressEvent (object o, Gtk.KeyPressEventArgs args)
		{
			SetModified();
		}

		protected virtual void OnDescrEntryKeyPressEvent (object o, Gtk.KeyPressEventArgs args)
		{
			SetModified();
		}

		protected virtual void OnNameEntryEditingDone (object sender, System.EventArgs e)
		{
			SetModified();
		}
		
		protected virtual void OnEmailEntryEditingDone (object sender, System.EventArgs e)
		{
			SetModified();
		}

		protected virtual void OnDescrEntryEditingDone (object sender, System.EventArgs e)
		{
			SetModified();
		}

		protected virtual void OnNameEntryKeyReleaseEvent (object o, Gtk.KeyReleaseEventArgs args)
		{
			SetModified();
		}

		protected virtual void OnEmailEntryKeyReleaseEvent (object o, Gtk.KeyReleaseEventArgs args)
		{
			SetModified();
		}

		protected virtual void OnDescrEntryKeyReleaseEvent (object o, Gtk.KeyReleaseEventArgs args)
		{
			SetModified();
		}
		
		protected virtual void OnNameEntryFocusGrabbed (object sender, System.EventArgs e)
		{
			entryFocus = nameEntry;
		}

		protected virtual void OnEmailEntryFocusGrabbed (object sender, System.EventArgs e)
		{
			entryFocus = emailEntry;
		}		

		protected virtual void OnDescrEntryFocusGrabbed (object sender, System.EventArgs e)
		{
			entryFocus = descrEntry;
		}
		
		protected virtual void OnCortarActionActivated (object sender, System.EventArgs e)
		{
			if (entryFocus != null) entryFocus.CutClipboard();
			SetModified();
			PasteAction.Sensitive = true;
		}

		protected virtual void OnCopiarActionActivated (object sender, System.EventArgs e)
		{
			if (entryFocus != null) entryFocus.CopyClipboard();
			PasteAction.Sensitive = true;
		}

		protected virtual void OnPegarActionActivated (object sender, System.EventArgs e)
		{
			if (entryFocus != null) entryFocus.PasteClipboard();
			SetModified();
		}

		protected virtual void OnAcercaDeActionActivated (object sender, System.EventArgs e)
		{
			if (AboutDialog == null)
			{
				AboutDialog = new AboutDialog();
			}
			
			AboutDialog.Run();
		}

		protected virtual void OnHelpAction1Activated (object sender, System.EventArgs e)
		{
			
		}

		protected virtual void OnHelpAction2Activated (object sender, System.EventArgs e)
		{
			try
			{
				
				// Importante! ... sin esto no se abre el archivo de ayuda!
				Gnome.Program gp = new Gnome.Program(Defines.PACKAGE, Defines.VERSION,Gnome.Modules.UI, new string[]{""});
				
				Gnome.Help.DisplayDesktopOnScreen (
					Gnome.Program.Get(),
				    Defines.GNOME_HELP_DIR,
					Defines.PACKAGE + ".xml",
					null,
					this.Screen);
				
			}
			catch (Exception ex)
			{
				Console.WriteLine(ex);
			}
		}

		protected virtual void OnDeleteActionActivated (object sender, System.EventArgs e)
		{
			if (confirm(Catalog.GetString("Are you sure?")))
			{
				Console.WriteLine("borrando...");
				ContactsManager.deleteContactById(ContactNumber);
				TotalContacts = ContactsManager.getTotalContacts();
				if (ContactNumber > TotalContacts)
				{
					ContactNumber = TotalContacts;
				}
				Contact c = ContactsManager.getContact(ContactNumber);
				SetContact(c);
				ShowContact();
			}
		}


		private Boolean confirm(string message)
		{
			ConfirmDialog cd = new ConfirmDialog(message);
			int retval = cd.Run();
			Console.WriteLine("retval = {0} ", retval);
			cd.Destroy();
			
			// TODO cambiar -5 por GTK.CONSTANTE_OK
			if (retval == -5) return true;
			else return false;
		}

	}	
}