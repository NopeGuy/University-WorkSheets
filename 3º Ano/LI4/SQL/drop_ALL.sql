USE li4
																						/*drop*/
ALTER TABLE [dbo].[vendedor_has_sala] DROP CONSTRAINT [FK_vendedor_has_sala_vendedor]
GO

ALTER TABLE [dbo].[vendedor_has_sala] DROP CONSTRAINT [FK_vendedor_has_sala_sala]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[vendedor_has_sala]') AND type in (N'U'))
DROP TABLE [dbo].[vendedor_has_sala]
GO

																						/*drop*/
ALTER TABLE [dbo].[venda] DROP CONSTRAINT [FK_venda_sala]
GO

ALTER TABLE [dbo].[venda] DROP CONSTRAINT [FK_venda_vendedor]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[venda]') AND type in (N'U'))
DROP TABLE [dbo].[venda]
GO

																						/*drop*/
ALTER TABLE [dbo].[chat] DROP CONSTRAINT [FK_chat_utilizador]
GO

ALTER TABLE [dbo].[chat] DROP CONSTRAINT [FK_chat_sala]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[chat]') AND type in (N'U'))
DROP TABLE [dbo].[chat]
GO

																						/*drop*/
ALTER TABLE [dbo].[sala] DROP CONSTRAINT [FK_sala_comprador]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sala]') AND type in (N'U'))
DROP TABLE [dbo].[sala]
GO

																						/*drop*/
ALTER TABLE [dbo].[denuncia] DROP CONSTRAINT [FK_denunciador_utilizador]
GO

ALTER TABLE [dbo].[denuncia] DROP CONSTRAINT [FK_denunciado_utilizador]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[denuncia]') AND type in (N'U'))
DROP TABLE [dbo].[denuncia]
GO


																						/*drop*/
ALTER TABLE [dbo].[comprador] DROP CONSTRAINT [FK_comprador_utilizador]
GO


IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[comprador]') AND type in (N'U'))
DROP TABLE [dbo].[comprador]
GO


																						/*drop*/
ALTER TABLE [dbo].[vendedor] DROP CONSTRAINT [FK_vendedor_utilizador]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[vendedor]') AND type in (N'U'))
DROP TABLE [dbo].[vendedor]
GO


																						/*drop*/
ALTER TABLE [dbo].[admin] DROP CONSTRAINT [FK_admin_utilizador]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[admin]') AND type in (N'U'))
DROP TABLE [dbo].[admin]
GO


																						/*drop*/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[utilizador]') AND type in (N'U'))
DROP TABLE [dbo].[utilizador]
GO