using Mono.Data.SqliteClient;
using System;
using System.Data;
using System.Data.Common;

namespace ContactsSharp
{
	public class Contact
	{		
		public Contact(int id, string name, string email, string description)
		{
			this.Id = id;
			this.Name = name;
			this.Email = email;
			this.Description = description;
		}
		
		public int Id { get; set; }
		public string Name { get; set; }
		public string Email { get; set; }
		public string Description { get; set; }
	}
	
	public class ContactsManager
	{
		string DataPath;
		string ConnectionString;
		IDbConnection dbcon;
		
		public ContactsManager()
		{
		}
		
		public void InitDatabase()
		{
			
			// Inicio la ruta a la base de datos sqlite
			DataPath = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData);
			DataPath = System.IO.Path.Combine(DataPath, "contacts-sharp");
			System.IO.Directory.CreateDirectory(DataPath);
			DataPath = System.IO.Path.Combine(DataPath, "contacts.db");
			
			// Si la base de datos/tabla no existe, la creo (CREATE TABLE)
			ConnectionString = "URI=file:" + DataPath;			
			dbcon = (IDbConnection) new SqliteConnection(ConnectionString);
			dbcon.Open();
			IDbCommand dbcmd = dbcon.CreateCommand();
			try
			{
				// TODO usar Log4Net
				Console.WriteLine("Looking for an existing database...");
				
				// hago un SELECT sobre la tabla CONTACTS. Si no la encuentro
				// entonces creo la tabla.
				dbcmd.CommandText = "select * from CONTACTS where ID = 0";
				IDataReader reader = dbcmd.ExecuteReader();
				while(reader.Read()) { } // no me interesan los registros, si los hubiera
				reader.Close();
				reader = null;
				dbcmd.Dispose();
				dbcmd = null;
				
				// TODO usar Log4Net
				Console.WriteLine("Database found ok...");
			}
			catch (Exception ex)
			{
				// TODO usar Log4Net
				Console.WriteLine("Creating database...");
				
				// Crear la tabla si hubo una excepcion (tabla no encontrada)
				dbcmd = dbcon.CreateCommand();
				dbcmd.CommandText = "" +
					" create table CONTACTS " +
					" (id INT AUTOINCREMENT PRIMARY KEY, name TEXT, email TEXT, description TEXT)";
				dbcmd.ExecuteNonQuery();
				dbcmd.Dispose();
				dbcmd = null;
			}			
			
		}
			
		public Boolean SaveContact(Contact contact)
		{
			try
			{
				IDbCommand dbcmd = dbcon.CreateCommand();
				dbcmd.CommandText = "" +
					" insert into CONTACTS (NAME, EMAIL, DESCRIPTION) values " +
					" (:name, :email, :desc)";
				dbcmd.CommandType = CommandType.Text;
				
				DbParameter nameParam = new SqliteParameter(":name", DbType.String) { Value = contact.Name };
				DbParameter emailParam = new SqliteParameter(":email", DbType.String) { Value = contact.Email };
				DbParameter descParam = new SqliteParameter(":desc", DbType.String) { Value = contact.Description };
				
				dbcmd.Parameters.Add(nameParam);
				dbcmd.Parameters.Add(emailParam);
				dbcmd.Parameters.Add(descParam);
				
				dbcmd.ExecuteNonQuery();
				dbcmd.Dispose();
				dbcmd = null;
				return true;
			}
			catch (Exception ex)
			{
				Console.WriteLine(ex.ToString());
				return false;
			}

		}
	
		public Boolean UpdateContact(Contact contact)
		{
			try
			{
				IDbCommand dbcmd = dbcon.CreateCommand();
				dbcmd.CommandText = "" +
					" update CONTACTS set NAME = :name, EMAIL = :email, " +
					" DESCRIPTION = :desc where ROWID = :id";
				dbcmd.CommandType = CommandType.Text;
				
				DbParameter nameParam = new SqliteParameter(":name", DbType.String) { Value = contact.Name };
				DbParameter emailParam = new SqliteParameter(":email", DbType.String) { Value = contact.Email };
				DbParameter descParam = new SqliteParameter(":desc", DbType.String) { Value = contact.Description };
				DbParameter idParam = new SqliteParameter(":id", DbType.Int32) { Value = contact.Id };
				
				dbcmd.Parameters.Add(nameParam);
				dbcmd.Parameters.Add(emailParam);
				dbcmd.Parameters.Add(descParam);
				dbcmd.Parameters.Add(idParam);
				
				dbcmd.ExecuteNonQuery();
				dbcmd.Dispose();
				dbcmd = null;
				return true;
			}
			catch (Exception ex)
			{
				Console.WriteLine(ex.ToString());
				return false;
			}

		}
		
		public Contact getContact(int contactNumber)
		{
			try
			{				
				IDbCommand command = dbcon.CreateCommand();
				command.CommandType = CommandType.Text;				
				command.CommandText = "" +
					" select rowid, name, email, description " +
					" from CONTACTS where rowid >= :min order by rowid asc limit 1";
				DbParameter param = new SqliteParameter(":min", DbType.Int32){ Value = contactNumber };
				command.Parameters.Add(param);			
				IDataReader reader = command.ExecuteReader();
				Contact contact = new Contact(0, "", "", "");
				Console.WriteLine("searching contact...");
				while (reader.Read())
				{					
					int id = reader.GetInt32(0);
					string name = reader.GetString(1);
					string email = reader.GetString(2);
					string descr = reader.GetString(3);
					contact = new Contact(id, name, email, descr);
					Console.WriteLine("found #{0} {1} {2} {3}", id, name, email, descr);
				}
				return contact;
			}
			catch (Exception ex)
			{
				Console.WriteLine(ex);
				return new Contact(1, "", "", "");
			}
			
		}
	
		public int getTotalContacts()
		{
			try
			{
				IDbCommand dbcmd = dbcon.CreateCommand();
				dbcmd.CommandText = "select count(*) from CONTACTS";
				dbcmd.CommandType = CommandType.Text;
				IDataReader reader = dbcmd.ExecuteReader();
				while (reader.Read()){
					return reader.GetInt32(0);
				}
			}
			catch(Exception ex)
			{
				Console.WriteLine(ex);
			}
			
			return 0;
			
		}
		
		public Boolean deleteContactById(int id)
		{
			try
			{
				IDbCommand dbcmd = dbcon.CreateCommand();
				dbcmd.CommandText = "delete from CONTACTS where ROWID = :id";
				dbcmd.CommandType = CommandType.Text;
				
				DbParameter idParam = new SqliteParameter(":id", DbType.Int32) { Value = id };				
				dbcmd.Parameters.Add(idParam);
				
				dbcmd.ExecuteNonQuery();
				dbcmd.Dispose();
				dbcmd = null;
				return true;
			}
			catch (Exception ex)
			{
				Console.WriteLine(ex);
				return false;
			}
		}
	}
}
