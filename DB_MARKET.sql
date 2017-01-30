create table admin(aid INT PRIMARY KEY,
						
						login text,
						mdp text );

create table market(mid INT PRIMARY KEY,
						
						login text,
						mdp text,
						typemarket text,
						city text );

INSERT INTO public.admin (aid, login, mdp) VALUES (1, 'AS', 'AS');
INSERT INTO public.market (mid, login, mdp, typemarket, city) VALUES (55, 'B', 'B', 'GENERAL', 'LILLE');
INSERT INTO public.market (mid, login, mdp, typemarket, city) VALUES (59, 'A', 'A', 'GENERAL', 'PARIS');