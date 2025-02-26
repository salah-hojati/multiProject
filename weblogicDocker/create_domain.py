# WebLogic WLST script to create a new domain

readTemplate("/opt/weblogic/wlserver/common/templates/wls/wls.jar")

# Set Domain Name
cd('/')
set('Name', 'base_domain')

# Configure Admin Server
cd('/Servers/AdminServer')
set('ListenAddress', '0.0.0.0')
set('ListenPort', 7001)

# Set WebLogic Credentials
cd('/')
cd('/Security/base_domain/User/weblogic')
cmo.setPassword('Admin1234')

# Write and close domain
setOption('OverwriteDomain', 'true')
writeDomain('/opt/weblogic/user_projects/domains/base_domain')
closeTemplate()

print('WebLogic domain created successfully!')
