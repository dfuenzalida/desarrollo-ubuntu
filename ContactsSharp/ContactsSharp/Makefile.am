# Los archivos que quiero que sean parte de la distribucion en tarball
EXTRA_DIST = \
	contacts-sharp.desktop

#desktop_in_files = contacts-sharp.desktop.in
#desktop_files = $(desktop_in_files:.desktop.in=.desktop)

desktopdir = $(datadir)/applications
#desktopdir = $(prefix)/share/applications
#desktop_DATA = $(desktop_files)
desktop_DATA = contacts-sharp.desktop

# Warning: This is an automatically generated file, do not edit!

if ENABLE_DEBUG
ASSEMBLY_COMPILER_COMMAND = gmcs
ASSEMBLY_COMPILER_FLAGS =  -noconfig -codepage:utf8 -warn:3 -optimize- -debug "-define:DEBUG" "-main:ContactsSharp.MainClass"
ASSEMBLY = bin/Debug/ContactsSharp.exe
ASSEMBLY_MDB = $(ASSEMBLY).mdb
COMPILE_TARGET = exe
PROJECT_REFERENCES = 
BUILD_DIR = bin/Debug

CONTACTSSHARP_EXE_MDB_SOURCE=bin/Debug/ContactsSharp.exe.mdb
CONTACTSSHARP_EXE_MDB=$(BUILD_DIR)/ContactsSharp.exe.mdb

endif

if ENABLE_RELEASE
ASSEMBLY_COMPILER_COMMAND = gmcs
ASSEMBLY_COMPILER_FLAGS =  -noconfig -codepage:utf8 -warn:3 -optimize- "-main:ContactsSharp.MainClass"
ASSEMBLY = bin/Release/ContactsSharp.exe
ASSEMBLY_MDB = 
COMPILE_TARGET = exe
PROJECT_REFERENCES = 
BUILD_DIR = bin/Release

CONTACTSSHARP_EXE_MDB=

endif

AL=al2
SATELLITE_ASSEMBLY_NAME=$(notdir $(basename $(ASSEMBLY))).resources.dll

PROGRAMFILES = \
	$(CONTACTSSHARP_EXE_MDB)  

BINARIES = \
	$(CONTACTS_SHARP)  


RESGEN=resgen2
	
# all: $(ASSEMBLY) $(PROGRAMFILES) $(BINARIES) HELP_FILES
all: $(ASSEMBLY) $(PROGRAMFILES) $(BINARIES)

FILES =  \
	AboutDialog.cs \
	AssemblyInfo.cs \
	ConfirmDialog.cs \
	ContactsManager.cs \
	Defines.cs \
	gtk-gui/ContactsSharp.AboutDialog.cs \
	gtk-gui/ContactsSharp.ConfirmDialog.cs \
	gtk-gui/ContactsSharp.MainWindow.cs \
	gtk-gui/generated.cs \
	Main.cs \
	MainWindow.cs 

DATA_FILES = 

RESOURCES = gtk-gui/gui.stetic 

EXTRAS = \
	contacts-sharp.in \
	Defines.cs.in

REFERENCES =  \
	Mono.Data.SqliteClient \
	Mono.Posix \
	-pkg:glade-sharp-2.0 \
	-pkg:glib-sharp-2.0 \
	-pkg:gnome-sharp-2.0 \
	-pkg:gtk-sharp-2.0 \
	System \
	System.Data

DLL_REFERENCES = 

CLEANFILES = $(PROGRAMFILES) $(BINARIES) \
	contacts-sharp \
	Defines.cs

include $(top_srcdir)/Makefile.include

CONTACTS_SHARP = $(BUILD_DIR)/contacts-sharp

$(eval $(call emit-deploy-wrapper,CONTACTS_SHARP,contacts-sharp,x))


$(eval $(call emit_resgen_targets))
$(build_xamlg_list): %.xaml.g.cs: %.xaml
	xamlg '$<'

# Denis 20090528
HELP_FILE = \
	help/contactssharp.xml

HELP_FILES:
	echo "HELP_FILE dir: $(shell dirname $(HELP_FILE))"
	echo "PREFIX: $(prefix)"
	echo "PACKAGE: $(PACKAGE)"
	mkdir -p $(prefix)/share/gnome/help/$(PACKAGE)
	cp -R ../$(shell dirname $(HELP_FILE))/* $(prefix)/share/gnome/help/$(PACKAGE)


$(ASSEMBLY) $(ASSEMBLY_MDB): $(build_sources) $(build_resources) $(build_datafiles) $(DLL_REFERENCES) $(PROJECT_REFERENCES) $(build_xamlg_list) $(build_satellite_assembly_list)
	mkdir -p $(shell dirname $(ASSEMBLY))
	$(ASSEMBLY_COMPILER_COMMAND) $(ASSEMBLY_COMPILER_FLAGS) -out:$(ASSEMBLY) -target:$(COMPILE_TARGET) $(build_sources_embed) $(build_resources_embed) $(build_references_ref)
