/* UNIVERSIDAD DEL VALLE DE GUATEMALA
 *
 * 
 * MODIFIED BY:  JOSE PABLO CASTILLO RODAS   10007  
 *
 * VER REPOSITORIO DE MODIFICACION E IMPLEMENTACION:
 * https://github.com/jpcastillo23/proyectomysql/
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESSS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * Copyright (c) 2014 by Bart Kiers
 * Developed by : Bart Kiers, bart@big-o.nl
 * MODIFIED BY: JOSE PABLO CASTILLO, cas10007@uvg.edu.gt
 * /Users/josecastillo/Netbeans/Mi_Sql_pequeño/src/PRUEBAS_ANTLR_MISQLGRAMMAR
 */
 
grammar MISQLGRAMMAR;
/*********************************************************************************
 *              PRIMARY_SINTAX ES LA GRAMATICA INICIAL EN CUYO CASO
 * 				TAMBIEN TIRA ERROR EN CASO NO COINCIDE CON LA GRAMATICA
 * 				ESTABLECIDA. PD: EOF => END OF FILE CONDITION (NOT CHARACTER)
 *				EN MAC ES ^D
 *********************************************************************************/
primary_sintax
 : ( sql_stmt_list | error )* EOF
 ;

error
 : UNEXPECTED_CHAR 
   { 
     System.out.println("error encontrado en la sintaxis" + $UNEXPECTED_CHAR.text +" " );
     throw new RuntimeException("UNEXPECTED_CHAR=" + $UNEXPECTED_CHAR.text); 
   } 
 ;
/*---*___*---*___*---*___*---*___*---*___*---*___*---*___*---*___ */
//       SQL  STATEMENT
sql_stmt_list
 : ';'* sql_stmt ( ';'+ sql_stmt )* ';'*

 ;
/*---*___*---*___*---*___*---*___*---*___*---*___*---*___*---*___ */
// TODAS LAS INSTRUCCIONES QUE SE PUEDEN IMPLEMENTAR
sql_stmt
 : ( K_EXPLAIN ( K_QUERY K_PLAN )? )? ( alter_table_stmt
                                      | analyze_stmt
                                      | attach_stmt
                                      | begin_stmt
                                      | commit_stmt
                                      | compound_select_stmt
                                      | create_database_stmt
                                      | create_index_stmt
                                      | create_table_stmt
                                      | create_trigger_stmt
                                      | create_view_stmt
                                      | create_virtual_table_stmt
                                      | delete_stmt
                                      | delete_stmt_limited
                                      | detach_stmt
                                      | use_stmt
                                      | drop_index_stmt
                                      | drop_table_stmt
                                      | drop_trigger_stmt
                                      | drop_view_stmt
                                      | factored_select_stmt
                                      | insert_stmt
                                      | pragma_stmt
                                      | reindex_stmt
                                      | release_stmt
                                      | rollback_stmt
                                      | savepoint_stmt
                                      | simple_select_stmt
                                      | select_stmt
                                      | update_stmt
                                      | update_stmt_limited
                                      | vacuum_stmt )
 ;
alter_table_stmt
 : K_ALTER K_TABLE ( database_name '.' )? table_name
   ( K_RENAME K_TO new_table_name
   | K_ADD K_COLUMN? column_def
   )
 ;

analyze_stmt
 : K_ANALYZE ( database_name | table_or_index_name | database_name '.' table_or_index_name )?
 ;

attach_stmt
 : K_ATTACH K_DATABASE? expr K_AS database_name
 ;

begin_stmt
 : K_BEGIN ( K_DEFERRED | K_IMMEDIATE | K_EXCLUSIVE )? ( K_TRANSACTION transaction_name? )?
 ;

commit_stmt
 : ( K_COMMIT | K_END ) ( K_TRANSACTION transaction_name? )?
 ;

compound_select_stmt
 : ( K_WITH K_RECURSIVE? common_table_expression ( ',' common_table_expression )* )?
   select_core ( ( K_UNION K_ALL? | K_INTERSECT | K_EXCEPT ) select_core )+
   ( K_ORDER K_BY ordering_term ( ',' ordering_term )* )?
   ( K_LIMIT expr ( ( K_OFFSET | ',' ) expr )? )?
 ;
create_database_stmt
 : K_CREATE K_DATABASE ( K_IF K_NOT K_EXISTS )?  database_name 
 ;
 use_stmt
 : K_USE database_name
 ;
create_index_stmt
 : K_CREATE K_UNIQUE? K_INDEX ( K_IF K_NOT K_EXISTS )?
   ( database_name '.' )? index_name K_ON table_name '(' indexed_column ( ',' indexed_column )* ')'
   ( K_WHERE expr )?
 ;

create_table_stmt										
 : K_CREATE ( K_TEMP | K_TEMPORARY )? K_TABLE ( K_IF K_NOT K_EXISTS )?
   ( database_name '.' )? table_name
   ( '(' column_def ( ',' column_def )* ( ',' table_constraint )* ')' ( K_WITHOUT IDENTIFIER )? | K_AS select_stmt  ) 
 ;

create_trigger_stmt
 : K_CREATE ( K_TEMP | K_TEMPORARY )? K_TRIGGER ( K_IF K_NOT K_EXISTS )?
   ( database_name '.' )? trigger_name ( K_BEFORE  | K_AFTER | K_INSTEAD K_OF )? 
   ( K_DELETE | K_INSERT | K_UPDATE ( K_OF column_name ( ',' column_name )* )? ) K_ON ( database_name '.' )? table_name
   ( K_FOR K_EACH K_ROW )? ( K_WHEN expr )?
   K_BEGIN ( ( update_stmt | insert_stmt | delete_stmt | select_stmt ) ';' )+ K_END
 ;

create_view_stmt
 : K_CREATE ( K_TEMP | K_TEMPORARY )? K_VIEW ( K_IF K_NOT K_EXISTS )?
   ( database_name '.' )? view_name K_AS select_stmt
 ;

create_virtual_table_stmt
 : K_CREATE K_VIRTUAL K_TABLE ( K_IF K_NOT K_EXISTS )?
   ( database_name '.' )? table_name
   K_USING module_name ( '(' module_argument ( ',' module_argument )* ')' )?
 ;

delete_stmt
 : with_clause? K_DELETE K_FROM qualified_table_name 
   ( K_WHERE expr )?
 ;

delete_stmt_limited
 : with_clause? K_DELETE K_FROM qualified_table_name 
   ( K_WHERE expr )?
   ( ( K_ORDER K_BY ordering_term ( ',' ordering_term )* )?
     K_LIMIT expr ( ( K_OFFSET | ',' ) expr )?
   )?
 ;

detach_stmt
 : K_DETACH K_DATABASE? database_name
 ;

drop_index_stmt
 : K_DROP K_INDEX ( K_IF K_EXISTS )? ( database_name '.' )? index_name
 ;

drop_table_stmt
 : K_DROP K_TABLE ( K_IF K_EXISTS )? ( database_name '.' )? table_name
 ;

drop_trigger_stmt
 : K_DROP K_TRIGGER ( K_IF K_EXISTS )? ( database_name '.' )? trigger_name
 ;

drop_view_stmt
 : K_DROP K_VIEW ( K_IF K_EXISTS )? ( database_name '.' )? view_name
 ;

factored_select_stmt
 : ( K_WITH K_RECURSIVE? common_table_expression ( ',' common_table_expression )* )?
   select_core ( compound_operator select_core )*
   ( K_ORDER K_BY ordering_term ( ',' ordering_term )* )?
   ( K_LIMIT expr ( ( K_OFFSET | ',' ) expr )? )?
 ;

insert_stmt
 : with_clause? ( K_INSERT 
                | K_REPLACE
                | K_INSERT K_OR K_REPLACE
                | K_INSERT K_OR K_ROLLBACK
                | K_INSERT K_OR K_ABORT
                | K_INSERT K_OR K_FAIL
                | K_INSERT K_OR K_IGNORE ) K_INTO
   ( database_name '.' )? table_name ( '(' column_name ( ',' column_name )* ')' )?
   ( K_VALUES '(' expr ( ',' expr )* ')' ( ',' '(' expr ( ',' expr )* ')' )*
   | select_stmt
   | K_DEFAULT K_VALUES
   )
 ;

pragma_stmt
 : K_PRAGMA ( database_name '.' )? pragma_name ( '=' pragma_value
                                               | '(' pragma_value ')' )?
 ;

reindex_stmt
 : K_REINDEX ( collation_name
             | ( database_name '.' )? ( table_name | index_name )
             )?
 ;

release_stmt
 : K_RELEASE K_SAVEPOINT? savepoint_name
 ;

rollback_stmt
 : K_ROLLBACK ( K_TRANSACTION transaction_name? )? ( K_TO K_SAVEPOINT? savepoint_name )?
 ;

savepoint_stmt
 : K_SAVEPOINT savepoint_name
 ;

simple_select_stmt
 : ( K_WITH K_RECURSIVE? common_table_expression ( ',' common_table_expression )* )?
   select_core ( K_ORDER K_BY ordering_term ( ',' ordering_term )* )?
   ( K_LIMIT expr ( ( K_OFFSET | ',' ) expr )? )?
 ;

select_stmt
 : ( K_WITH K_RECURSIVE? common_table_expression ( ',' common_table_expression )* )?
   select_or_values ( compound_operator select_or_values )*
   ( K_ORDER K_BY ordering_term ( ',' ordering_term )* )?
   ( K_LIMIT expr ( ( K_OFFSET | ',' ) expr )? )?
 ;

select_or_values
 : K_SELECT ( K_DISTINCT | K_ALL )? result_column ( ',' result_column )*
   ( K_FROM ( table_or_subquery ( ',' table_or_subquery )* | join_clause ) )?
   ( K_WHERE expr )?
   ( K_GROUP K_BY expr ( ',' expr )* ( K_HAVING expr )? )?
 | K_VALUES '(' expr ( ',' expr )* ')' ( ',' '(' expr ( ',' expr )* ')' )*
 ;

update_stmt
 : with_clause? K_UPDATE ( K_OR K_ROLLBACK
                         | K_OR K_ABORT
                         | K_OR K_REPLACE
                         | K_OR K_FAIL
                         | K_OR K_IGNORE )? qualified_table_name
   K_SET column_name '=' expr ( ',' column_name '=' expr )* ( K_WHERE expr )?
 ;

update_stmt_limited
 : with_clause? K_UPDATE ( K_OR K_ROLLBACK
                         | K_OR K_ABORT
                         | K_OR K_REPLACE
                         | K_OR K_FAIL
                         | K_OR K_IGNORE )? qualified_table_name
   K_SET column_name '=' expr ( ',' column_name '=' expr )* ( K_WHERE expr )?
   ( ( K_ORDER K_BY ordering_term ( ',' ordering_term )* )?
     K_LIMIT expr ( ( K_OFFSET | ',' ) expr )? 
   )?
 ;

vacuum_stmt
 : K_VACUUM
 ;

column_def
 : column_name type_name? column_constraint*
 ;

type_name
 : name+ ( '(' signed_number ')'
         | '(' signed_number ',' signed_number ')' )?
 ;

column_constraint
 : ( K_CONSTRAINT name )?
   ( K_PRIMARY K_KEY ( K_ASC | K_DESC )? conflict_clause K_AUTOINCREMENT?
   | K_NOT? K_NULL conflict_clause
   | K_UNIQUE conflict_clause
   | K_CHECK '(' expr ')'
   | K_DEFAULT (signed_number | literal_value | '(' expr ')')
   | K_COLLATE collation_name
   | foreign_key_clause
   )
 ;

conflict_clause
 : ( K_ON K_CONFLICT ( K_ROLLBACK
                     | K_ABORT
                     | K_FAIL
                     | K_IGNORE
                     | K_REPLACE
                     )
   )?
 ;

/*
 **********************    OPERADORES DE FUNCIONES *********************************
    SQLite understands the following binary operators, in order from highest to
    lowest precedence:

    ||
    *    /    %
    +    -
    <<   >>   &    |
    <    <=   >    >=
    =    ==   !=   <>   IS   IS NOT   IN   LIKE   GLOB   MATCH   REGEXP
    AND
    OR
*/
expr
 : literal_value												#expressionNumeral
 | BIND_PARAMETER											#bindexpression
 | ( ( database_name '.' )? table_name '.' )? column_name		#expressionDBTableCol
 | unary_operator expr										#unitaryexpression
 | expr '||' expr											#expressionOR
 | expr ( '*' | '/' | '%' ) expr								#mulDivexpression
 | expr ( '+' | '-' ) expr									#addRestexpression
 | expr ( '<<' | '>>' | '&' | '|' ) expr						#bitManiexpression
 | expr ( '<' | '<=' | '>' | '>=' ) expr						#comparationExpression
 | expr ( '=' | '==' | '!=' | '<>' | K_IS | K_IS K_NOT | K_IN | K_LIKE | K_GLOB | K_MATCH | K_REGEXP ) expr #equexpression
 | expr K_AND expr											#andwhileexpression
 | expr K_OR expr											#orwhileexpression
 | function_name '(' ( K_DISTINCT? expr ( ',' expr )* | '*' )? ')'   #funtionexpression
 | '(' expr ')'												#parenexpression
 | K_CAST '(' expr K_AS type_name ')'						#assustituteexpression
 | expr K_COLLATE collation_name								#collationexpression
 | expr K_NOT? ( K_LIKE | K_GLOB | K_REGEXP | K_MATCH ) expr ( K_ESCAPE expr )?  #aglomeraexpression
 | expr ( K_ISNULL | K_NOTNULL | K_NOT K_NULL )				#nullcompexpression
 | expr K_IS K_NOT? expr										#isornotexpression
 | expr K_NOT? K_BETWEEN expr K_AND expr						#compnotbetweenexpression
 | expr K_NOT? K_IN ( '(' ( select_stmt | expr ( ',' expr )* )? ')'  | ( database_name '.' )? table_name )	 #notinexpression	
 | ( ( K_NOT )? K_EXISTS )? '(' select_stmt ')'				#notexistexpression
 | K_CASE expr? ( K_WHEN expr K_THEN expr )+ ( K_ELSE expr )? K_END #wheneventexpression
 | raise_function											#joinexpression
 ;

foreign_key_clause
 : K_REFERENCES foreign_table ( '(' column_name ( ',' column_name )* ')' )?
   ( ( K_ON ( K_DELETE | K_UPDATE ) ( K_SET K_NULL
                                    | K_SET K_DEFAULT
                                    | K_CASCADE
                                    | K_RESTRICT
                                    | K_NO K_ACTION )
     | K_MATCH name
     ) 
   )*
   ( K_NOT? K_DEFERRABLE ( K_INITIALLY K_DEFERRED | K_INITIALLY K_IMMEDIATE )? )?
 ;

raise_function
 : K_RAISE '(' ( K_IGNORE 
               | ( K_ROLLBACK | K_ABORT | K_FAIL ) ',' error_message )
           ')'
 ;

indexed_column
 : column_name ( K_COLLATE collation_name )? ( K_ASC | K_DESC )?
 ;

table_constraint
 : ( K_CONSTRAINT name )?
   ( ( K_PRIMARY K_KEY | K_UNIQUE ) '(' indexed_column ( ',' indexed_column )* ')' conflict_clause
   | K_CHECK '(' expr ')'
   | K_FOREIGN K_KEY '(' column_name ( ',' column_name )* ')' foreign_key_clause
   )
 ;

with_clause
 : K_WITH K_RECURSIVE? cte_table_name K_AS '(' select_stmt ')' ( ',' cte_table_name K_AS '(' select_stmt ')' )*
 ;

qualified_table_name
 : ( database_name '.' )? table_name ( K_INDEXED K_BY index_name
                                     | K_NOT K_INDEXED )?
 ;

ordering_term
 : expr ( K_COLLATE collation_name )? ( K_ASC | K_DESC )?
 ;

pragma_value
 : signed_number
 | name
 | STRING_LITERAL
 ;

common_table_expression
 : table_name ( '(' column_name ( ',' column_name )* ')' )? K_AS '(' select_stmt ')'
 ;

result_column
 : '*'
 | table_name '.' '*'
 | expr ( K_AS? column_alias )?
 ;

table_or_subquery
 : ( database_name '.' )? table_name ( K_AS? table_alias )?
   ( K_INDEXED K_BY index_name
   | K_NOT K_INDEXED )?
 | '(' ( table_or_subquery ( ',' table_or_subquery )*
       | join_clause )
   ')' ( K_AS? table_alias )?
 | '(' select_stmt ')' ( K_AS? table_alias )?
 ;

join_clause
 : table_or_subquery ( join_operator table_or_subquery join_constraint )*
 ;
 
 

join_operator
 : ','
 | K_NATURAL? ( K_LEFT K_OUTER? | K_INNER | K_CROSS )? K_JOIN
 ;

join_constraint
 : ( K_ON expr
   | K_USING '(' column_name ( ',' column_name )* ')' )?
 ;

select_core
 : K_SELECT ( K_DISTINCT | K_ALL )? result_column ( ',' result_column )*
   ( K_FROM ( table_or_subquery ( ',' table_or_subquery )* | join_clause ) )?
   ( K_WHERE expr )?
   ( K_GROUP K_BY expr ( ',' expr )* ( K_HAVING expr )? )?
 | K_VALUES '(' expr ( ',' expr )* ')' ( ',' '(' expr ( ',' expr )* ')' )*
 ;

compound_operator
 : K_UNION
 | K_UNION K_ALL
 | K_INTERSECT
 | K_EXCEPT
 ;

cte_table_name
 : table_name ( '(' column_name ( ',' column_name )* ')' )?
 ;

signed_number
 : ( '+' | '-' )? NUMERIC_LITERAL
 ;

literal_value
 : NUMERIC_LITERAL
 | STRING_LITERAL
 | BLOB_LITERAL
 | K_NULL
 | K_CURRENT_TIME
 | K_CURRENT_DATE
 | K_CURRENT_TIMESTAMP
 ;

unary_operator
 : '-'
 | '+'
 | '~'
 | K_NOT
 ;

error_message
 : STRING_LITERAL
 ;

module_argument // TODO check what exactly is permitted here
 : expr
 | column_def
 ;

column_alias
 : IDENTIFIER
 | STRING_LITERAL
 ;

keyword
 : K_ABORT
 | K_ACTION
 | K_ADD
 | K_AFTER
 | K_ALL
 | K_ALTER
 | K_ANALYZE
 | K_AND
 | K_AS
 | K_ASC
 | K_ATTACH
 | K_AUTOINCREMENT
 | K_BEFORE
 | K_BEGIN
 | K_BETWEEN
 | K_BY
 | K_CASCADE
 | K_CASE
 | K_CAST
 | K_CHECK
 | K_COLLATE
 | K_COLUMN
 | K_COMMIT
 | K_CONFLICT
 | K_CONSTRAINT
 | K_CREATE
 | K_CROSS
 | K_USE
 | K_CURRENT_DATE
 | K_CURRENT_TIME
 | K_CURRENT_TIMESTAMP
 | K_DATABASE
 | K_DEFAULT
 | K_DEFERRABLE
 | K_DEFERRED
 | K_DELETE
 | K_DESC
 | K_DETACH
 | K_DISTINCT
 | K_DROP
 | K_EACH
 | K_ELSE
 | K_END
 | K_ESCAPE
 | K_EXCEPT
 | K_EXCLUSIVE
 | K_EXISTS
 | K_EXPLAIN
 | K_FAIL
 | K_FOR
 | K_FOREIGN
 | K_FROM
 | K_FULL
 | K_GLOB
 | K_GROUP
 | K_HAVING
 | K_IF
 | K_IGNORE
 | K_IMMEDIATE
 | K_IN
 | K_INDEX
 | K_INDEXED
 | K_INITIALLY
 | K_INNER
 | K_INSERT
 | K_INSTEAD
 | K_INTERSECT
 | K_INTO
 | K_IS
 | K_ISNULL
 | K_JOIN
 | K_KEY
 | K_LEFT
 | K_LIKE
 | K_LIMIT
 | K_MATCH
 | K_NATURAL
 | K_NO
 | K_NOT
 | K_NOTNULL
 | K_NULL
 | K_OF
 | K_OFFSET
 | K_ON
 | K_OR
 | K_ORDER
 | K_OUTER
 | K_PLAN
 | K_PRAGMA
 | K_PRIMARY
 | K_QUERY
 | K_RAISE
 | K_RECURSIVE
 | K_REFERENCES
 | K_REGEXP
 | K_REINDEX
 | K_RELEASE
 | K_RENAME
 | K_REPLACE
 | K_RESTRICT
 | K_RIGHT
 | K_ROLLBACK
 | K_ROW
 | K_SAVEPOINT
 | K_SELECT
 | K_SET
 | K_TABLE
 | K_TEMP
 | K_TEMPORARY
 | K_THEN
 | K_TO
 | K_TRANSACTION
 | K_TRIGGER
 | K_UNION
 | K_UNIQUE
 | K_UPDATE
 | K_USING
 | K_VACUUM
 | K_VALUES
 | K_VIEW
 | K_VIRTUAL
 | K_WHEN
 | K_WHERE
 | K_WITH
 | K_WITHOUT
 ;

// TODO check all names below

name
 : any_name
 ;

function_name
 : any_name
 ;

database_name
 : any_name
 ;

table_name 
 : any_name
 ;

table_or_index_name 
 : any_name
 ;

new_table_name 
 : any_name
 ;

column_name 
 : any_name
 ;

collation_name 
 : any_name
 ;

foreign_table 
 : any_name
 ;

index_name 
 : any_name
 ;

trigger_name
 : any_name
 ;

view_name 
 : any_name
 ;

module_name 
 : any_name
 ;

pragma_name 
 : any_name
 ;

savepoint_name 
 : any_name
 ;

table_alias 
 : any_name
 ;

transaction_name
 : any_name
 ;

any_name
 : IDENTIFIER 
 | keyword
 | STRING_LITERAL
 | '(' any_name ')'
 ;

/*****************************************************************************
 **********************         SIMBOLOS CLAVE        ************************
 *****************************************************************************/
SCOL : 		';';
DOT : 		'.';
OPEN_PAR : 	'(';
CLOSE_PAR : ')';
COMMA : 	',';
ASSIGN : 	'=';
STAR : 		'*';
PLUS : 		'+';
MINUS : 	'-';
TILDE : 	'~';
PIPE2 : 	'||';
DIV : 		'/';
MOD : 		'%';
LT2 : 		'<<';
GT2 : 		'>>';
AMP : 		'&';
PIPE : 		'|';
LT : 		'<';
LT_EQ : 	'<=';
GT : 		'>';
GT_EQ : 	'>=';
EQ : 		'==';
NOT_EQ1 : 	'!=';
NOT_EQ2 : 	'<>';

/*****************************************************************************
 **********************         PALABRAS CLAVE        ************************
 *****************************************************************************/
K_ABORT : 		'ABORT';
K_ACTION : 		'ACTION';
K_ADD : 		'ADD';
K_AFTER : 		'AFTER';
K_ALL : 		'ALL';
K_ALTER : 		'ALTER';
K_ANALYZE : 	'ANALYZE';
K_AND : 		'AND';
K_AS : 			'AS';
K_ASC : 		'ASC';
K_ATTACH : 		'ATTACH';
K_AUTOINCREMENT : 'AUTOINCREMENT';
K_BEFORE : 		'BEFORE';
K_BEGIN : 		'BEGIN';
K_BETWEEN : 	'BETWEEN';
K_BY : 			'BY';
K_CASCADE : 	'CASCADE';
K_CASE : 		'CASE';
K_CAST : 		'CAST';
K_CHECK : 		'CHECK';
K_COLLATE : 	'COLLATE';
K_COLUMN : 		'COLUMN';
K_COMMIT : 		'COMMIT';
K_CONFLICT : 	'CONFLICT';
K_CONSTRAINT : 	'CONSTRAINT';
K_CREATE : 		'CREATE';
K_CROSS : 		'CROSS';
K_CURRENT_DATE : 		'CURRENT_DATE';
K_CURRENT_TIME : 		'CURRENT_TIME';
K_CURRENT_TIMESTAMP : 	'CURRENT_TIMESTAMP';
K_DATABASE : 	'DATABASE';
K_DEFAULT : 	'DEFAULT';
K_DEFERRABLE : 	'DEFERRABLE';
K_DEFERRED : 	'DEFERRED';
K_DELETE : 		'DELETE';
K_DESC :		'DESC';
K_DETACH : 		'DETACH';
K_DISTINCT : 	'DISTINCT';
K_DROP :  		'DROP';
K_EACH : 		'EACH';
K_ELSE : 		'ELSE';
K_END : 		'END';
K_ESCAPE : 		'ESCAPE';
K_EXCEPT : 		'EXCEPT';
K_EXCLUSIVE : 	'EXCLUSIVE';
K_EXISTS : 		'EXISTS';
K_EXPLAIN : 	'EXPLAIN';
K_FAIL : 		'FAIL';
K_FOR : 		'FOR';
K_FOREIGN : 	'FOREIGN';
K_FROM : 		'FROM';
K_FULL : 		'FULL';
K_GLOB : 		'GLOB';
K_GROUP : 		'GROUP';
K_HAVING : 		'HAVING';
K_IF : 			'IF';
K_IGNORE : 		'IGNORE';
K_IMMEDIATE : 	'IMMEDIATE';
K_IN : 			'IN';
K_INDEX : 		'INDEX';
K_INDEXED : 	'INDEXED';
K_INITIALLY : 	'INITIALLY';
K_INNER : 		'INNER';
K_INSERT : 		'INSERT';
K_INSTEAD : 	'INSTEAD';
K_INTERSECT :	'INTERSECT';
K_INTO : 		'INTO';
K_IS : 			'IS';
K_ISNULL : 		'ISNULL';
K_JOIN : 		'JOIN';
K_KEY : 		'KEY';
K_LEFT : 		'LEFT';
K_LIKE : 		'LIKE';
K_LIMIT : 		'LIMIT';
K_MATCH : 		'MATCH';
K_NATURAL : 	'NATURAL';
K_NO : 			'NO';
K_NOT : 		'NOT';
K_NOTNULL : 	'NOTNULL';
K_NULL : 		'NULL';
K_OF : 			'OF';
K_OFFSET : 		'OFFSET';
K_ON : 			'ON';
K_OR : 			'OR';
K_ORDER : 		'ORDER';
K_OUTER : 		'OUTER';
K_PLAN : 		'PLAN';
K_PRAGMA : 		'PRAGMA';
K_PRIMARY : 	'PRIMARY';
K_QUERY : 		'QUERY';
K_RAISE : 		'RAISE';
K_RECURSIVE : 	'RECURSIVE';
K_REFERENCES : 	'REFERENCES';
K_REGEXP : 		'REGEXP';
K_REINDEX : 	'REINDEX';
K_RELEASE : 	'RELEASE';
K_RENAME : 		'RENAME';
K_REPLACE : 	'REPLACE';
K_RESTRICT : 	'RESTRICT';
K_RIGHT : 		'RIGHT';
K_ROLLBACK : 	'ROLLBACK';
K_ROW : 		'ROW';
K_SAVEPOINT : 	'SAVEPOINT';
K_SELECT : 		'SELECT';
K_SET : 		'SET';
K_TABLE : 		'TABLE';
K_TEMP : 		'TEMP';
K_TEMPORARY : 	'TEMPORARY';
K_THEN : 		'THEN';
K_TO : 			'TO';
K_TRANSACTION : 'TRANSACTION';
K_TRIGGER : 	'TRIGGER';
K_UNION : 		'UNION';
K_UNIQUE : 		'UNIQUE';
K_UPDATE : 		'UPDATE';
K_USING : 		'USING';
K_USE	:		'USE';
K_VACUUM : 		'VACUUM';
K_VALUES :		'VALUES';
K_VIEW : 		'VIEW';
K_VIRTUAL : 	'VIRTUAL';
K_WHEN : 		'WHEN';
K_WHERE : 		'WHERE';
K_WITH : 		'WITH';
K_WITHOUT : 	'WITHOUT';
//*---*---*---*---*---*---*---*---*---*---*---*---*---*---*--- 
//FORMATO PARA UN STRING O COMO SE ESCRIBE UN IDENTIFICADOR CORRECTAMENTE
//DE LA FORMA   hagoPRUEBA23 , declaraVAR01
IDENTIFIER
 : '"' (~'"' | '""')* '"'
 | '`' (~'`' | '``')* '`'
 | '[' ~']'* ']'
 | [a-zA-Z_][a-zA-Z_0-9]* // TODO check: needs more chars in set
 ;
//*---*---*---*---*---*---*---*---*---*---*---*---*---*---*--- 
// NUMERO TAL QUE SEA DE LA SIGUIENTE FORMA   ##.##?   O .## 
// Y LA 'E' ES DE  1E6  = 1000000

NUMERIC_LITERAL
 : DIGITDOS+ ( '.' DIGIT* )? ( E [-+]? DIGIT+ )?
 | '.' DIGIT+ ( E [-+]? DIGIT+ )?
 ;
//*---*---*---*---*---*---*---*---*---*---*---*---*---*---*--- 
BIND_PARAMETER
 : '?' DIGIT*
 | [:@$] IDENTIFIER
 ;

STRING_LITERAL
 : '\'' ( ~'\'' | '\'\'' )* '\''
 ;

BLOB_LITERAL
 : X STRING_LITERAL
 ;

SINGLE_LINE_COMMENT
 : '--' ~[\r\n]* -> channel(HIDDEN)
 ;

MULTILINE_COMMENT
 : '/*' .*? ( '*/' | EOF ) -> channel(HIDDEN)
 ;

SPACES
 : [ \u000B\t\r\n] -> channel(HIDDEN)
 ;
//   EL PUNTO '.'  SIGNIFICA CUALQUIER CARACTER
UNEXPECTED_CHAR
 : . ;

 
// ESTO ME SIRVE PARA CREAR NUMEROS LOS CUALES NO CONTENGAN UN MILLON
// DE CEROS NO SIGNIFICATIVOS

fragment DIGITDOS
			: DIGIT|[1-9] DIGIT+;
fragment DIGIT
			: [0-9];
fragment FLOAT: DIGIT '.' DIGITDOS+ ;


// PUEDEN ESCRIBIR EN MAYUSCULAS Y MINUSCULAS CUALQUIER LETRA, NO HAY RESTRICCION EN LA 
// FORMA DE INGRESO. 

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];
