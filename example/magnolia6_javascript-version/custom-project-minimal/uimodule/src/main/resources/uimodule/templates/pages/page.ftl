<!DOCTYPE html>
<html>
  <head>
    [@cms.page /]
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  </head>
  <body>
    <h1>Frontify field example</h1>
    [#assign frontifyField = content.frontifyField!]
    <b>Frontify Field:</b> <br />
    <img style="width: 500px;" src="${frontifyField}" alt="" />
  </body>
</html>
