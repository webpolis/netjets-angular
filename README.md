# netjets
## Apache configuration

Add the following to the virtual host configuration in Apache server:

```
RewriteCond %{REQUEST_URI} ^/socket/$
RewriteRule ^/(.*) ws://localhost:8888/$1 [P,L]
```

