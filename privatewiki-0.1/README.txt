Introduction
-------------------------------------------------------------------------

Privatewiki is a simple application to help you in the way you store
your notes today.

Today, you possibly take your notes in paper, plain textfiles all over
your hard-disk and such. You use them once and send them to the trash,
so you lose context and relationship they have with previous annotations.

Privatewiki helps you to store your notes in a simple application, based
in open standards. Your notes are all added to a single datafile which
you can carry in a USB pendrive.

Each document displays in a wiki-style, so you see related concepts and
you can navigate your annotations easily.

The embedded search will help you to find specific notes quickly.

Running Privatewiki
-------------------------------------------------------------------------

This 0.1 beta release requires Sun JDK or JRE 1.4+. On Windows, double
click the jar file. On Windows or Linux, you can run it with 

	java -jar privatewiki-0.1-binsrc.jar

This release requires a password to start. The password is "wiki" but can be
changed later to any password you want.

Tweaking Configuration
-------------------------------------------------------------------------

This release doesn't implement a configuration tool for settings such as
the "Start page" or fonts used for display. This can be changed unpacking
the data.zip file and tweaking the 'config' file in the 'meta' folder, then
pack everything in a data.zip file with the same structure and run privatewiki
again.

This way, you can do nice things such as enabling Japanese language support
(it worked to me, in WinXP).

-------------------------------------------------------------------------
Denis Fuenzalida,
2005.02.21
