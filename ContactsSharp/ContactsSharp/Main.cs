using Gtk;
using Mono.Data.SqliteClient;
using Mono.Unix;
using System;
using System.Data;

namespace ContactsSharp
{
	class MainClass
	{
		public static void Main (string[] args)
		{            
			// Inicializo la base de datos
			ContactsManager contactsManager = new ContactsManager();
			contactsManager.InitDatabase();
			
			// I18n
			Catalog.Init("contactssharp", Defines.LOCALE_DIR);
			
			// Inicializo la interfaz de usuario
			Application.Init();
			MainWindow win = new MainWindow();
						
			// Cargo el primero Contacto desde la base de datos
			win.ContactsManager = contactsManager;
			Contact firstContact = contactsManager.getContact(1);
			win.TotalContacts = contactsManager.getTotalContacts();
			win.SetContact(firstContact);
			if (win.TotalContacts == 0)
			{
				win.Nuevo = true;
			}
			
			// Despliego la ventana
			win.Show();
			Application.Run();
		}
	}
}
