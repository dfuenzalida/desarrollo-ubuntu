#
# Regular cron jobs for the contactssharp package
#
0 4	* * *	root	[ -x /usr/bin/contactssharp_maintenance ] && /usr/bin/contactssharp_maintenance
